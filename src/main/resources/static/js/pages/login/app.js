const REQUEST_URL = '/api/login';
const MAIN_PAGE = '/main';

$(document).ready(function () {

    $('#loginForm').on('submit', function (e) {
        e.preventDefault();
        loginUser();
    });

    /**
     * 로그인 요청을 서버에 전송하고 결과에 따라 처리
     *
     * - 사용자가 입력한 로그인 정보를 JSON으로 변환해 POST 요청으로 전송
     * - 응답이 성공이면 메인 페이지로 리다이렉트
     * - 실패 시 에러 메시지를 화면에 표시
     */
    function loginUser() {
        fetchData(REQUEST_URL, {
            method: HTTP_METHODS.POST,
            data: JSON.stringify(getLoginInfo()),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                showSuccessAndRedirectAlert(MAIN_PAGE);
            })
            .catch(error => {
                handleSubmitErrorResponse(JSON.parse(error));
            });
    }

    function getLoginInfo() {
        return {
            name: $('#name').val().trim(),
            password: $('#password').val()
        };
    }
});

/**
 * 로그인 성공 시 성공 알림을 띄우고, 확인 버튼 클릭 후 지정된 URL로 이동하는 함수
 *
 * @param {string} redirectUrl - 로그인 후 이동할 URL
 */
function showSuccessAndRedirectAlert(redirectUrl) {
    showOneButtonAlert({
        title: '로그인되었습니다.',
        alertType: 'success',
        callback() {
            window.location.href = redirectUrl;
        }
    });
}

/**
 * 로그인 실패 시 사용자에게 실패 사유를 알림창으로 표시하는 함수
 *
 * @param {Object} error - 서버로부터 받은 에러 응답 객체
 */
function handleSubmitErrorResponse(error) {
    if (error.statusCode === HTTP_STATUS.BAD_REQUEST) {
        showOneButtonAlert({
            title: '로그인에 실패했습니다',
            text: error.message,
            alertType: 'error',
            callback() {
            }
        });
    } else {
        throw error;
    }
}
