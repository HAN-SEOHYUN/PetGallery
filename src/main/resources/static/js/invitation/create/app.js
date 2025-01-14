const REQUEST_URL = '/api/invitations';
const UPLOAD_URL = '/api/images'
const MAIN_PAGE = '/invitations/main';
let mainImageId = null;

$(document).ready(function () {
    const $fileInput = $('#fileInput');
    const $previewImage = $('#previewImage');

    /*파일 선택 관련*/
    $('#mainImage').on('click', () => $fileInput.click());

    $fileInput.on('change', handleFileChange);

    /* form 제출 */
    $('#submitBtn').on('click', function (event) {
        event.preventDefault();

        if (!validateInputs() || !validateImageAttached()) { return; }

        showTwoButtonAlert({
            title: '등록하시겠습니까 ?',
            text: '',
            alertType: 'info',
            callback() {
                addData(REQUEST_URL, getFormInfo());
            }
        });
    });

    function validateInputs() {
        let isValid = true;

        $('input[required], textarea[required]').each(function () {
            if (!$(this).val().trim()) {
                alert(`"${$(this).prev('label').text()}" 항목을 입력해주세요.`);
                $(this).focus();
                isValid = false;
                return false;
            }
        });
        return isValid;
    }

    function validateImageAttached() {
        const fileInput = $('#fileInput')[0];
        if (!fileInput.files || fileInput.files.length === 0) {
            alert('메인 이미지를 첨부해주세요.');
            $('#fileInput').focus();
            return false;
        }
        return true;
    }

    function handleFileChange(event) {
        const file = event.target.files[0];

        if (file) {
            previewImage(file);
            addFile(file);
        } else {
            $previewImage.hide(); // 파일이 선택되지 않으면 미리보기 숨기기
        }
    }

    function previewImage(file) {
        const reader = new FileReader();
        reader.onload = e => $previewImage.attr('src', e.target.result).show();
        reader.readAsDataURL(file);
    }

    function addFile(file) {
        uploadFile(UPLOAD_URL, { file })
            .then((response) => {
                mainImageId = response;
            })
            .catch((error) => {
                handleUploadErrorResponse(error);
            });
    }

    function addData(url, requestData) {
        fetchData(url, {
            method: HTTP_METHODS.POST,
            headers: {
                'Content-Type': 'application/json',
            },
            data: JSON.stringify(requestData)
        })
            .then(response => { // 성공
                showSuccessAndRedirectAlert(MAIN_PAGE);
            })
            .catch(error => { // 실패
                handleSubmitErrorResponse(JSON.parse(error));
            });
    }

    function getFormInfo() {
        return data = {
            brideInfo: getBrideInfo(),
            groomInfo: getGroomInfo(),
            date: $('#weddingDate').val(),
            letterTxt: $('#letterTxt').val(),
            mainTxt: $('#mainTxt').val(),
            greetTxt: $('#greetTxt').val(),
            mainImageId : mainImageId
        }
    }

    function getBrideInfo() {
        return {
            name: $('#brideName').val(),
            birth: $('#brideBirth').val(),
            phone: $('#bridePhone').val(),
            fatherName: $('#brideFatherName').val(),
            fatherPhone: $('#brideFatherPhone').val(),
            fatherDeceasedYN: getCheckboxValue('#brideFatherDeceasedYN'),
            motherName: $('#brideMotherName').val(),
            motherPhone: $('#brideMotherPhone').val(),
            motherDeceasedYN: getCheckboxValue('#brideMotherDeceasedYN')
        };
    }

    function getGroomInfo() {
        return {
            name: $('#groomName').val(),
            birth: $('#groomBirth').val(),
            phone: $('#groomPhone').val(),
            fatherName: $('#groomFatherName').val(),
            fatherPhone: $('#groomFatherPhone').val(),
            fatherDeceasedYN: getCheckboxValue('#groomFatherDeceasedYN'),
            motherName: $('#groomMotherName').val(),
            motherPhone: $('#groomMotherPhone').val(),
            motherDeceasedYN: getCheckboxValue('#groomMotherDeceasedYN')
        };
    }

    function getCheckboxValue(selector) {
        return $(selector).prop('checked') ? 'Y' : 'N';
    }
});

/**
 * 업로드 실패 시 실패 사유를 사용자에게 알리는 함수입니다.
 *
 * @param {String} error - API에서 반환된 에러 응답.
 *
 *   {
 *     timestamp: '2025-01-14T09:11:03.599+00:00',
 *     status: 405,
 *     error: 'Method Not Allowed',
 *     path: '/invitations/create'
 *   }
 */
function handleUploadErrorResponse(error) {
    const errorResponse = JSON.parse(error);
    showOneButtonAlert({
        title: '사진 첨부에 실패했습니다. 재시도 해주세요.',
        text: errorResponse.error,
        alertType: 'error',
        callback() {
            $('#previewImage').hide();
        }
    });
}

/**
 * 등록 실패 시 실패 사유를 알려주는 함수
 *
 * @param {JSON} error - API에서 응답받은 데이터
 */
function handleSubmitErrorResponse(error) {
    if (error.statusCode === HTTP_STATUS.BAD_REQUEST) {
        showOneButtonAlert({
            title: '등록에 실패했습니다',
            text: error.errorMsg,
            alertType: 'error',
            callback() {}
        });
    } else {
        throw error;
    }
}

/**
 * 등록 완료 후 알림을 표시하고, 알림이 닫히면 리다이렉트하는 함수
 *
 * @param {string} redirectUrl - 리다이렉트할 주소 (예: '/invitations/main')
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



