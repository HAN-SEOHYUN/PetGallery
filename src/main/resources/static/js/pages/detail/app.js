const REQUEST_URL = '/api';
const MAIN_PAGE = '/list';

$(document).ready(function () {
    const accessKey = getAccessKeyFromUrl();
    const deleteBtn = $("#submitBtn");
    getData(accessKey);

    //삭제 버튼 클릭
    deleteBtn.on("click", handleDeleteClick);

    function handleDeleteClick() {
        try {
            const accessKey = getValidAccessKey();
            confirmDelete(accessKey);
        } catch (error) {
            handleDeleteException(error);
        }
    }

    /**
     * URL에서 accessKey를 추출하고 검증
     * @returns {string} 유효한 accessKey
     * @throws {Error} accessKey가 없거나 유효하지 않을 경우
     */
    function getValidAccessKey() {
        const accessKey = getAccessKeyFromUrl();
        if (!accessKey || typeof accessKey !== 'string') {
            throw new Error("InvalidAccessKey");
        }
        return accessKey;
    }

    /**
     * 삭제 여부를 사용자에게 확인받고, 삭제 요청 수행
     * @param {string} accessKey
     */
    function confirmDelete(accessKey) {
        showTwoButtonAlert({
            title: '삭제하시겠습니까?',
            text: '삭제된 내용은 복구할 수 없습니다.',
            alertType: 'warn',
            callback() {
                deleteInvitation(accessKey);
            }
        });
    }

    /**
     * 예외 발생 시 사용자에게 알림 표시
     * @param {Error} error
     */
    function handleDeleteException(error) {
        if (error.message === "INVALID_ACCESS" || error.statusCode === "LOGIN_REQUIRED") {
            showOneButtonAlert({
                title: '잘못된 접근입니다',
                text: error.message,
                alertType: 'warning',
                callback() {}
            });
        } else {
            console.error("삭제 버튼 처리 중 예외 발생:", error);
            showOneButtonAlert({
                title: '오류 발생',
                text: '삭제 처리 중 문제가 발생했습니다.',
                alertType: 'error',
                callback() {}
            });
        }
    }

    // 초대장 삭제 요청을 보내는 함수
    function deleteInvitation(accessKey) {
        const url = `${REQUEST_URL}/?accessKey=${encodeURIComponent(accessKey)}`;

        fetchData(url, {
            method: HTTP_METHODS.DELETE,
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(() => {
                console.log(`Invitation ${accessKey} deleted successfully.`);
                showSuccessAndRedirectAlert(MAIN_PAGE);
            })
            .catch(error => {
                handleSubmitErrorResponse(JSON.parse(error));
            });
    }

    function getData(accessKey) {
        const url = `${REQUEST_URL}/?accessKey=${encodeURIComponent(accessKey)}`;
        fetchData(url, {
            method: HTTP_METHODS.GET,
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => { // 성공
                populateInvitationDetail(response.data);
            })
            .catch(error => { // 실패
            });
    }

    // 이미지 설정 함수
    function setMainImage(mainImageData) {
        const $mainImageContainer = $(".main-image-container");
        if (mainImageData?.s3Url) {
            $mainImageContainer.html(
                `<img src="${mainImageData.s3Url}" alt="${mainImageData.orgFileName}" class="main-image">`
            );
        } else {
            $mainImageContainer.html("이미지가 없습니다.");
        }
    }

    function setImageList(imageList) {

        if (!Array.isArray(imageList) || imageList.length === 0) {
            console.error("Invalid image list data");
        }
        
        const $imageListContainer = $(".image-list-container");
        $imageListContainer.empty();

        imageList.forEach(image => {
            const $imgElement = $("<img>")
                .attr("src", image.s3Url)
                .attr("alt", image.orgFileName);

            $imageListContainer.append($imgElement);
        });
    }

    function bindPetInfo(data) {
        bindData("#petBirth", formatKoreanDate(data.date));
        bindData("#petAge", `${calculateAge(data.date)}살`);
        bindData("#petName", data.name);
        bindData("#introText", data.introText);
        bindData("#likeWord", data.likeWord);
        bindData("#hateWord", data.hateWord);
        bindData("#likeCount", data.likeCount);
        bindData("#regDate", formatDateOnly(data.regTime));

    }

    // 신부 정보 바인딩
    function bindOwnerInfo(owner) {
        bindData("#ownerName", owner.name);
        bindData("#ownerBirth", owner.birth);
        bindData("#ownerPhone", owner.phone);
        bindData("#ownerFatherName", owner.fatherName);
        bindData("#ownerFatherPhone", owner.fatherPhone);
        setCheckbox("#ownerFatherDeceasedYN", owner.fatherDeceasedYN);
        bindData("#ownerMotherName", owner.motherName);
        bindData("#ownerMotherPhone", owner.motherPhone);
        setCheckbox("#ownerMotherDeceasedYN", owner.motherDeceasedYN);
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
        bindPetInfo(data);
        setMainImage(data.mainImage);
        toggleDeleteButton(data.hasDeletePermission);
    }

    function toggleDeleteButton(hasDeletePermission) {
        if (hasDeletePermission === true) {
           deleteBtn.show();
        } else {
           deleteBtn.remove(); // 노출도 안 하고 DOM에서 제거
        }
    }

    // 체크박스를 설정하는 함수
    function setCheckbox(selector, condition) {
        $(selector).prop("checked", condition === "Y");
    }
    //데이터를 HTML 요소에 바인딩하는 함수
    function bindData(selector, data) {
        $(selector).text(data !== null && data !== undefined ? data : "정보 없음");
    }

    function setInvitationId(selector, data) {
        $(selector).val(data);
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

    function formatKoreanDate(dateStr) {
        const date = new Date(dateStr);
        const year = date.getFullYear();
        const month = date.getMonth() + 1; // 0-indexed
        const day = date.getDate();
        return `${year}년 ${month}월 ${day}일`;
    }

    function formatDateOnly(dateTimeStr) {
        if (!dateTimeStr) return '-';
        return dateTimeStr.split('T')[0]; // "2025-05-19T17:22:37.690495" → "2025-05-19"
    }
});

/**
 * 현재 페이지 URL의 쿼리 문자열에서 'accessKey' 파라미터 값을 추출
 * @returns {string | null} - 'accessKey' 파라미터 값 (없으면 null 반환)
 *
 */
function getAccessKeyFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get('accessKey');
}

/**
 * 삭제 완료 후 알림을 표시하고, 알림이 닫히면 리다이렉트하는 함수
 *
 * @param {string} redirectUrl - 리다이렉트할 주소 (예: '/pages/main')
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