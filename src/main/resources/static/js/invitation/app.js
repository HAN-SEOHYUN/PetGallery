const REQUEST_URL = "/api/invitations";
$(document).ready(function() {

    // 폼 제출 시 호출되는 함수
    $('#invitationForm').on('submit', function(event) {
        event.preventDefault(); // 기본 폼 제출 방지

        // 데이터 객체 빌드
        const data = {
            brideInfo: getBrideInfo(),
            groomInfo: getGroomInfo(),
            date: $('#weddingDate').val(),
            letterTxt: $('#letterTxt').val(),
            mainTxt: $('#mainTxt').val(),
            greetTxt: $('#greetTxt').val()
        };

        // 요청 보내기
        addData(REQUEST_URL,data);
    });

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

    function addData(url, data) {
        return fetchData(url, {
            method: HTTP_METHODS.POST,  // HTTP 메서드 POST
            headers: {
                'Content-Type': 'application/json',  // JSON 형식으로 전송
            },
            data: JSON.stringify(data)  // 요청 데이터는 JSON 문자열로 변환
        })
            .then(response => {
                console.log('Data added successfully:', response);
                return response;  // 성공적으로 응답받은 데이터 반환
            })
            .catch(error => {
                console.error('Error adding data:', error);
                throw error;  // 에러 발생 시 에러를 다시 던짐
            });
    }
});

