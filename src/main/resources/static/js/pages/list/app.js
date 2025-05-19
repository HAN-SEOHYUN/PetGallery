const REQUEST_URL = '/api';

$(document).ready(function () {
    const $grid = $('.grid');

    getList(0);

    /**
     * 메인 페이징 렌더링 함수.
     * 이전, 다음, 페이지 번호 버튼을 생성하고 이벤트 핸들러를 설정합니다.
     *
     * @param {Pagination} pagination - 페이지네이션 정보를 담은 객체.
     */
    function renderPagination(pagination) {
        const container = $('.pagination');
        container.empty(); // 기존의 페이징 항목들을 비움

        renderPreviousButton(container, pagination);
        renderPageButtons(container, pagination);
        renderNextButton(container, pagination);

        setupPaginationEventHandlers(container, pagination);
    }

    /**
     * 이전 버튼을 생성하여 페이징 컨테이너에 추가합니다.
     *
     * @param {jQuery} container - 페이징 버튼을 추가할 컨테이너 요소.
     * @param {Pagination} pagination - 페이지네이션 정보를 담은 객체.
     */
    function renderPreviousButton(container, pagination) {
        if (pagination.hasPrevious()) {
            container.append('<button class="prev">Previous</button>');
        }
    }

    /**
     * 페이지 번호 버튼을 생성하여 페이징 컨테이너에 추가함
     *
     * @param {jQuery} container - 페이징 버튼을 추가할 컨테이너 요소.
     * @param {Pagination} pagination - 페이지네이션 정보를 담은 객체.
     */
    function renderPageButtons(container, pagination) {
        pagination.pageRange.forEach(page => {
            const pageButton = $('<button>')
                .addClass('page')
                .text(page)
                .toggleClass('active', page - 1 === pagination.currentPage) // 현재 페이지 강조
                .on('click', function () {
                    pagination.setCurrentPage(page - 1);
                    getList(pagination.currentPage); // 버튼 누르면 해당 페이지 데이터 가져오기
                });
            container.append(pageButton);
        });
    }

    /**
     * > 버튼을 생성하여 페이징 컨테이너에 추가함
     *
     * @param {jQuery} container - 페이징 버튼을 추가할 컨테이너 요소.
     * @param {Pagination} pagination - 페이지네이션 정보를 담은 객체.
     */
    function renderNextButton(container, pagination) {
        if (pagination.hasNext()) {
            container.append('<button class="next">Next</button>');
        }
    }

    /**
     * <,> 버튼의 이벤트 핸들러를 설정함
     *
     * @param {jQuery} container - 페이징 버튼이 포함된 컨테이너 요소.
     * @param {Pagination} pagination - 페이지네이션 정보를 담은 객체.
     */
    function setupPaginationEventHandlers(container, pagination) {
        // 이전 버튼 클릭 이벤트
        container.find('.prev').on('click', function () {
            if (pagination.hasPrevious()) {
                pagination.setCurrentPage(pagination.currentPage - 1); // 이전 페이지로 이동
                getList(pagination.currentPage);
            }
        });

        // 다음 버튼 클릭 이벤트
        container.find('.next').on('click', function () {
            if (pagination.hasNext()) {
                pagination.setCurrentPage(pagination.currentPage + 1); // 다음 페이지로 이동
                getList(pagination.currentPage);
            }
        });
    }

    /**
     * 청첩장 리스트 목록을 요청하고 렌더링하는 함수.
     *
     * @param {number} page - 요청할 페이지 번호 (0부터 시작).
     */
    function getList(page) {
        const url = `${REQUEST_URL}?page=${page}&size=${PAGINATION_SIZE}&userId=${getUserId()}`;
        fetchData(url, {
            method: HTTP_METHODS.GET,
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => { // 성공
                // 카드 목록 생성
                const pagination = new Pagination(response.data);

                const contentHtml = getContentHtml(response.data.content);
                $grid.html(contentHtml);

                renderPagination(pagination);
            })
            .catch(error => { // 실패
                handleErrorResponse(error);
            });
    }
});

/**
 * 응답 데이터에 따라 카드 목록을 생성하거나, 게시물이 없다는 메시지를 반환하는 함수.
 *
 * @param {Array} data - 초대장 정보가 담긴 객체 배열.
 * @returns {string} - 카드 목록 또는 "등록된 게시물이 없습니다." 메시지.
 */
function getContentHtml(data) {
    if (data && data.length > 0) {
        return buildCardList(data);
    } else {
        // return '<p class="no-content">등록된 게시물이 없습니다.</p>';
        return '<p></p><p class="no-content">' +
            '<i class="fas fa-box-open"></i>\n' +
            '  아직 게시물이 없어요!</p></p>';
    }
}

/**
 * 카드 목록을 생성하는 함수.
 *
 * @param {Array} data - pet 정보가 담긴 객체 배열.
 * @returns {string} - 생성된 카드 목록을 포함하는 HTML 문자열.
 */
function buildCardList(data) {
    return data.map(pet => {
        const imageUrl = pet.mainImage && pet.mainImage.s3Url
            ? pet.mainImage.s3Url
            : 'https://img.icons8.com/ios/500/no-image.png';

        // 좋아요 여부에 따라 아이콘 클래스 설정
        const heartClass = pet.liked ? 'fas fa-heart' : 'far fa-heart';

        return `
            <div class="card" onclick="moveToDetail('${pet.accessKey}')">
                <img src="${imageUrl}" alt="Pet Main Photo">
                <div class="card-content">
                    <p class="name"><i class="fas fa-paw"></i> ${pet.name ? pet.name : ""}</p>
                    <p class="age">${calculateAge(pet.date)} 살</p>
                </div>
                
                <button class="like-btn" onclick="event.stopPropagation(); toggleLike(${pet.id}, this);">
                    <i class="${heartClass}"></i>
                </button>
            </div>
        `;
    }).join('');
}

/**
 * 사용자가 특정 반려동물(petId)에 대해 좋아요(하트) 토글 기능을 수행합니다.
 *
 * 1. 현재 로그인된 사용자 ID를 세션(HTML body dataset)에서 가져옵니다.
 * 2. 로그인 상태가 아니면 로그인 페이지로 유도하는 경고창을 띄웁니다.
 * 3. 로그인 상태면 서버에 좋아요 요청을 POST 방식으로 보냅니다.
 * 4. 요청 성공 시, 버튼의 하트 아이콘을 빈 하트 ↔ 채워진 하트로 토글하여 UI를 업데이트합니다.
 * 5. 요청 실패 시, 에러를 콘솔에 출력하고 필요하면 사용자에게 알림을 처리할 수 있도록 준비합니다.
 *
 * @param {number} petId - 좋아요를 누른 반려동물의 고유 ID
 * @param {HTMLElement} btnElement - 좋아요 버튼 요소 (아이콘 토글을 위해 필요)
 */
function toggleLike(petId, btnElement) {
    const userId = getUserId();
    if (!userId) {
        validateLogin();
        return;
    }

    const url = getRequestUrl(petId, userId);
    sendLikeRequest(url)
        .then(() => updateLikeIcon(btnElement))
        .catch((error) => handleLikeError(JSON.parse(error)));
}

function validateLogin() {
    showOneButtonAlert({
        title: '로그인이 필요합니다',
        alertType: 'warning',
        callback() {
            window.location.href = '/login';
        }
    });
}

function getRequestUrl(petId, userId) {
    return `/api/likes/${petId}?userId=${userId}`;
}

function sendLikeRequest(url) {
    return fetchData(url, {
        method: HTTP_METHODS.POST,
        headers: { 'Content-Type': 'application/json' }
    });
}

function updateLikeIcon(btnElement) {
    const icon = btnElement.querySelector('i');
    if (icon.classList.contains('fas')) {
        icon.classList.remove('fas', 'liked');
        icon.classList.add('far');
    } else {
        icon.classList.remove('far');
        icon.classList.add('fas', 'liked');
    }
}

function handleLikeError(error) {
    if (error.statusCode === HTTP_STATUS.BAD_REQUEST) {
        showOneButtonAlert({
            title: '좋아요 실패',
            text: error.message,
            alertType: 'error',
            callback() {
            }
        });
    } else {
        throw error;
    }
}


function calculateAge(dateStr) {
    const birthDate = new Date(dateStr);
    const today = new Date();

    let age = today.getFullYear() - birthDate.getFullYear();
    const hasHadBirthdayThisYear =
        today.getMonth() > birthDate.getMonth() ||
        (today.getMonth() === birthDate.getMonth() && today.getDate() >= birthDate.getDate());

    if (!hasHadBirthdayThisYear) {
        age--;
    }

    return age; // 나이 리턴
}


/**
 * accessKey 를 기반으로 상세 페이지로 이동
 * @param {String} accessKey - 청첩장 ID
 */
function moveToDetail(accessKey) {
    window.location.href = `/detail?accessKey=${accessKey}`;
}

/**
 * HTTP 요청에서 발생한 오류를 처리하는 함수.
 * 오류의 상태 코드가 BAD_REQUEST인 경우 사용자에게 오류 메시지를 보여줌
 * 그 외의 오류는 그대로 던져서 호출한 곳에서 처리
 *
 * @param {Object} error - 발생한 오류 객체.
 * @throws {Object} - `BAD_REQUEST` 오류가 아닌 경우 원래의 오류 객체를 다시 던짐
 */
function handleErrorResponse(error) {
    const errorResponse = JSON.parse(error);
    if (error.statusCode === HTTP_STATUS.BAD_REQUEST) {
        showOneButtonAlert({
            title: errorResponse.message,
            text: null,
            alertType: 'error',
            callback() {
                window.location.href = '/main';
            }
        });
    } else {
        throw error;
    }
}





