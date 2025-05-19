const REQUEST_URL = '/api/register';
const LOGIN_PAGE = '/login';

$(document).ready(function () {

    $('#registerForm').on('submit', function (e) {
        e.preventDefault();

        if (!isPasswordConfirmed()) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }

        showTwoButtonAlert({
            title: '가입하시겠습니까 ?',
            text: '',
            alertType: 'info',
            callback() {
                addData(REQUEST_URL, getRequestData());
            }
        });
    });

    function getRequestData() {
        const name = $('#name').val().trim();
        const password = $('#password').val();

        return {
            name: name,
            password: password
        };
    }

    /**
     * 이미지 데이터를 서버에 전송하여 등록하는 함수.
     *
     * @param {String} url - 데이터를 전송할 API URL.
     * @param {Object} requestData - 전송할 요청 데이터. JSON 형식으로 변환되어 서버로 전송됩니다.
     *
     */
    function addData(url, requestData) {
        fetchData(url, {
            method: HTTP_METHODS.POST,
            headers: {
                'Content-Type': 'application/json',
            },
            data: JSON.stringify(requestData)
        })
            .then(response => { // 성공
                showSuccessAndRedirectAlert(LOGIN_PAGE);
            })
            .catch(error => { // 실패
                handleSubmitErrorResponse(JSON.parse(error));
            });
    }

    function isPasswordConfirmed() {
        const password = $('#password').val();
        const confirmPassword = $('#confirm-password').val();

        return password === confirmPassword;
    }
});

/**
 * 가입 완료 후 알림을 표시하고, 알림이 닫히면 리다이렉트하는 함수
 *
 * @param {string} redirectUrl - 리다이렉트할 주소 (예: '/pages/main')
 */
function showSuccessAndRedirectAlert(redirectUrl) {
    showOneButtonAlert({
        title: '등록되었습니다.',
        alertType: 'success',
        callback() {
            window.location.href = redirectUrl;
        }
    });
}

/**
 * 가입 실패 시 실패 사유를 알려주는 함수
 *
 * @param {JSON} error - API에서 응답받은 데이터
 */
function handleSubmitErrorResponse(error) {
    if (error.statusCode === HTTP_STATUS.BAD_REQUEST) {
        showOneButtonAlert({
            title: '가입에 실패했습니다',
            text: error.message,
            alertType: 'error',
            callback() {
            }
        });
    } else {
        throw error;
    }
}
