const REQUEST_URL = '/api/invitations';
$(document).ready(function () {
    const $grid = $('.grid');

    getList(REQUEST_URL);

    function getList(url) {
        fetchData(url, {
            method: HTTP_METHODS.GET,
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => { // 성공
                $grid.html(buildCardList(response));
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



