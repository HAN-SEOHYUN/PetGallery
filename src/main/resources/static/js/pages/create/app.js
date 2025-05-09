const REQUEST_URL = '/api/pages';
const UPLOAD_URL = '/api/images'
const MAIN_PAGE = '/pages/main';
let imageIdList = [];

$(document).ready(function () {
    const $fileInput = $('#fileInput');
    const $previewImage = $('#previewImage');
    const $lottieSpinner = document.getElementById('lottie-spinner');

    // Lottie(spinner) 애니메이션 초기화
    let animation = lottie.loadAnimation({
        container: $lottieSpinner,
        renderer: 'svg',
        loop: true,
        autoplay: true,
        path: '/images/spinner.json'
    });

    /*파일 선택 관련*/
    $('#mainImage').on('click', () => $fileInput.click());

    $fileInput.on('change', handleFileChange);

    /* form 제출 */
    $('#submitBtn').on('click', function (event) {
        event.preventDefault();

        if (!validateInputs() || !validateImageAttached()) {
            return;
        }

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
        const files = event.target.files;

        if (files.length > 0) {
            $.each(files, function (index, file) {
                previewImage(file);
                addFile(file);
            });
        }
    }

    function previewImage(file) {
        if (!file) return; // 파일이 없으면 함수 종료

        const reader = new FileReader();
        reader.onload = function (e) {
            const img = $("<img>").attr("src", e.target.result).css({ width: "100px", height: "100px", margin: "5px" });
            $("#previewContainer").append(img);
        };
        reader.readAsDataURL(file);
    }

    /**
     * 파일을 S3 에 업로드하는 함수.
     *
     * @param {File} file - 업로드할 파일. 이 파일은 S3에 업로드됩니다.
     */
    function addFile(file) {
        toggleUploadState(true);
        uploadFile(UPLOAD_URL, {file})
            .then((response) => {
                imageIdList.push(response);
                console.log(imageIdList);
                toggleUploadState(false);
            })
            .catch((error) => {
                handleUploadErrorResponse(error);
                toggleUploadState(false);
            });
    }

    /**
     * 업로드 상태에 따라 로딩 애니메이션을 표시하거나 숨기고, 등록 버튼을 활성화/비활성화하는 함수.
     *
     * @param {boolean} isUploading - 업로드 중인지 여부를 나타내는 값,
     */
    function toggleUploadState(isUploading) {
        if (isUploading) {
            $lottieSpinner.style.display = 'block';  // 로딩 애니메이션 표시
        } else {
            $lottieSpinner.style.display = 'none';  // 로딩 애니메이션 숨기기
        }
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
            imageIdList:imageIdList,
            mainImageId: imageIdList[0] // 추후 변경 예정
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
 *     path: '/pages/create'
 *   }
 */
function handleUploadErrorResponse(error) {
    const errorResponse = JSON.parse(error);
    showOneButtonAlert({
        title: '사진 첨부에 실패했습니다. 재시도 해주세요.',
        text: errorResponse.error || errorResponse.errorMsg,
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
            callback() {
            }
        });
    } else {
        throw error;
    }
}

/**
 * 등록 완료 후 알림을 표시하고, 알림이 닫히면 리다이렉트하는 함수
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

function validateNumber(inputElement) {
    const value = inputElement.value;
    if (!/^\d*$/.test(value)) {
        inputElement.value = value.replace(/\D/g, ''); // 숫자가 아닌 값 제거
        //alert('숫자만 입력 가능합니다.');
        showOneButtonAlert({
            title: '숫자만 입력 가능합니다.',
            alertType: 'info',
            callback() {
            }
        });
    }
}



