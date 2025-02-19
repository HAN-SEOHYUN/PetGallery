const REQUEST_URL = '/api/invitations';
const MAIN_PAGE = '/invitations/main';

$(document).ready(function () {
    const invitationId = getInvitationIdFromUrl();
    getData(invitationId);

    $("#deleteBtn").on("click", function () {

        const invitationId = getInvitationId(this);
        if (!invitationId) return;

        showTwoButtonAlert({
            title: '삭제하시겠습니까 ?',
            text: '',
            alertType: 'error',
            callback() {
                deleteInvitation(invitationId, this);
            }
        });
    });

    function getInvitationId() {
        const invitationId = $("#invitationId").val();
        if (!invitationId) {
            console.error("삭제할 초대장 ID가 없습니다.");
            return null;
        }
        return invitationId;
    }

    // 초대장 삭제 요청을 보내는 함수
    function deleteInvitation(invitationId, button) {
        const url = `${REQUEST_URL}/${invitationId}`;
        console.log("url:",url);
        console.log("HTTP_METHODS.DELETE:", HTTP_METHODS.DELETE);

        fetchData(url, {
            method: HTTP_METHODS.DELETE,
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(() => {
                console.log(`Invitation ${invitationId} deleted successfully.`);
                //showSuccessAndRedirectAlert(MAIN_PAGE);
            })
            .catch(error => {
                handleSubmitErrorResponse(JSON.parse(error));
            });
    }

    function getData(invitationId) {
        const url = `${REQUEST_URL}/${invitationId}`;
        fetchData(url, {
            method: HTTP_METHODS.GET,
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => { // 성공
                populateInvitationDetail(response);
            })
            .catch(error => { // 실패

            });
    }

    // 이미지 설정 함수
    function setImage(imageData) {
        const $imagePlaceholder = $(".image-placeholder");
        if (imageData?.s3Url) {
            $imagePlaceholder.html(
                `<img src="${imageData.s3Url}" alt="${imageData.orgFileName}" class="main-image">`
            );
        } else {
            $imagePlaceholder.html("이미지가 없습니다.");
        }
    }

    // 결혼 정보 바인딩
    function bindWeddingInfo(data) {
        bindData("#weddingDate", data.date);
        bindData("#letterTxt", data.letterTxt);
        bindData("#mainTxt", data.mainTxt);
        bindData("#greetTxt", data.greetTxt);
        setInvitationId("#invitationId",data.id);
    }

    // 신부 정보 바인딩
    function bindBrideInfo(bride) {
        bindData("#brideName", bride.name);
        bindData("#brideBirth", bride.birth);
        bindData("#bridePhone", bride.phone);
        bindData("#brideFatherName", bride.fatherName);
        bindData("#brideFatherPhone", bride.fatherPhone);
        setCheckbox("#brideFatherDeceasedYN", bride.fatherDeceasedYN);
        bindData("#brideMotherName", bride.motherName);
        bindData("#brideMotherPhone", bride.motherPhone);
        setCheckbox("#brideMotherDeceasedYN", bride.motherDeceasedYN);
    }

    // 신랑 정보 바인딩
    function bindGroomInfo(groom) {
        bindData("#groomName", groom.name);
        bindData("#groomBirth", groom.birth);
        bindData("#groomPhone", groom.phone);
        bindData("#groomFatherName", groom.fatherName);
        bindData("#groomFatherPhone", groom.fatherPhone);
        setCheckbox("#groomFatherDeceasedYN", groom.fatherDeceasedYN);
        bindData("#groomMotherName", groom.motherName);
        bindData("#groomMotherPhone", groom.motherPhone);
        setCheckbox("#groomMotherDeceasedYN", groom.motherDeceasedYN);
    }

    // 데이터 바인딩 메인 함수
    function populateInvitationDetail(data) {
        bindWeddingInfo(data);
        bindBrideInfo(data.brideInfo);
        bindGroomInfo(data.groomInfo);
        setImage(data.mainImage);
    }

    // 체크박스를 설정하는 함수
    function setCheckbox(selector, condition) {
        $(selector).prop("checked", condition === "Y");
    }
    //데이터를 HTML 요소에 바인딩하는 함수
    function bindData(selector, data) {
        $(selector).text(data || "정보 없음");
    }
    function setInvitationId(selector, data) {
        $(selector).val(data);
    }
});

/**
 * 현재 페이지 URL의 쿼리 문자열에서 'id' 파라미터 값을 추출
 * @returns {string | null} - 'id' 파라미터 값 (없으면 null 반환)
 *
 */
function getInvitationIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get('id');
}

/**
 * 삭제 완료 후 알림을 표시하고, 알림이 닫히면 리다이렉트하는 함수
 *
 * @param {string} redirectUrl - 리다이렉트할 주소 (예: '/invitations/main')
 */
function showSuccessAndRedirectAlert(redirectUrl) {
    showOneButtonAlert({
        title: '삭제되었습니다.',
        alertType: 'success',
        callback() {
            window.location.href = redirectUrl;
        }
    });
}

/**
 * 삭제 실패 시 실패 사유를 알려주는 함수
 *
 * @param {JSON} error - API에서 응답받은 데이터
 */
function handleSubmitErrorResponse(error) {
    if (error.statusCode === HTTP_STATUS.BAD_REQUEST) {
        showOneButtonAlert({
            title: '삭제에 실패했습니다',
            text: error.errorMsg,
            alertType: 'error',
            callback() {}
        });
    } else {
        throw error;
    }
}