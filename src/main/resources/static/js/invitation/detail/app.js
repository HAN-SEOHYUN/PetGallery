const REQUEST_URL = '/api/invitations';

$(document).ready(function () {
    const invitationId = getInvitationIdFromUrl();
    getData(invitationId);

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