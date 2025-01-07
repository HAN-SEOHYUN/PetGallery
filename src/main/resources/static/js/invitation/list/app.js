const REQUEST_URL = '/api/invitations';

$(document).ready(function () {
    const $grid = $('.grid');

    getList(0);

    function renderPagination(pagination) {
        const container = $('.pagination'); // 페이징 컨테이너
        container.empty(); // 기존의 페이징 항목들을 비웁니다

        // 이전 버튼
        if (pagination.hasPrevious()) {
            container.append('<button class="prev">Previous</button>');
        }

        // 페이지 번호 버튼
        pagination.pageRange.forEach(page => {
            const pageButton = $('<button>')
                .addClass('page')
                .text(page)
                .on('click', function() {
                    pagination.setCurrentPage(page - 1); // 페이지 번호를 0 기반으로 설정
                    getList(pagination.currentPage); // 페이지 데이터 가져오기
                });

            if (page - 1 === pagination.currentPage) {
                pageButton.addClass('active'); // 현재 페이지 강조
            }

            container.append(pageButton);
        });

        // 다음 버튼
        if (pagination.hasNext()) {
            container.append('<button class="next">Next</button>');
        }

        // 이전 버튼 클릭 처리
        container.find('.prev').on('click', function() {
            if (pagination.hasPrevious()) {
                pagination.setCurrentPage(pagination.currentPage - 1);
                getList(pagination.currentPage);
            }
        });

        // 다음 버튼 클릭 처리
        container.find('.next').on('click', function() {
            if (pagination.hasNext()) {
                pagination.setCurrentPage(pagination.currentPage + 1);
                getList(pagination.currentPage);
            }
        });
    }

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
                $grid.html(buildCardList(response.content));
                renderPagination(pagination);
            })
            .catch(error => { // 실패
                handleErrorResponse(error);
            });
    }
});

/**
 * 카드 목록을 생성하는 함수.
 *
 * @param {Array} data - 초대장 정보가 담긴 객체 배열.
 * @returns {string} - 생성된 카드 목록을 포함하는 HTML 문자열.
 */
function buildCardList(data) {
    return data.map(invitation => {
        return `
            <div class="card">
                <img src="https://via.placeholder.com/300" alt="Invitation Main Photo">
                <div class="card-content">
                    <p class="letter">${invitation.letterTxt}</p>
                    <p class="date">${invitation.date}</p>
                </div>
            </div>
        `;
    }).join('');
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





