const REQUEST_URL = '/api/invitations';

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
        const url = `${REQUEST_URL}?page=${page}&size=${PAGINATION_SIZE}`;
        fetchData(url, {
            method: HTTP_METHODS.GET,
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => { // 성공
                // 카드 목록 생성
                const pagination = new Pagination(response);

                const contentHtml = getContentHtml(response.content);
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
        return '<p></p><p class="no-content">등록된 게시물이 없습니다.</p>';
    }
}

/**
 * 카드 목록을 생성하는 함수.
 *
 * @param {Array} data - 초대장 정보가 담긴 객체 배열.
 * @returns {string} - 생성된 카드 목록을 포함하는 HTML 문자열.
 */
function buildCardList(data) {
    return data.map(invitation => {
        const imageUrl = invitation.mainImage && invitation.mainImage.s3Url
            ? invitation.mainImage.s3Url
            : 'https://img.icons8.com/ios/500/no-image.png';

        return `
            <div class="card" onclick="moveToDetail(${invitation.id})">
                <img src=${imageUrl} alt="Invitation Main Photo">
                <div class="card-content">
                    <p class="letter">${invitation.letterTxt}</p>
                </div>
            </div>
        `;
    }).join('');
}

/**
 * Invitation ID를 기반으로 상세 페이지로 이동
 * @param {number} invitationId - 청첩장 ID
 */
function moveToDetail(invitationId) {
    window.location.href = `/invitations/detail?id=${invitationId}`;
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
    if (error.statusCode === HTTP_STATUS.BAD_REQUEST) {
        showOneButtonAlert({
            title: '조회에 실패했습니다.',
            text: '버튼을 클릭하시면 메인 화면으로 이동합니다.',
            alertType: 'error',
            callback() {
                window.location.href = '/invitations/main';
            }
        });
    } else {
        throw error;
    }
}





