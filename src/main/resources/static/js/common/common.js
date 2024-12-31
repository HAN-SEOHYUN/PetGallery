// 공통 jQuery AJAX 요청 함수 (리팩토링)
function fetchData(url, options = {}) {
    // 기본 설정 (method, headers, data 등)
    const settings = {
        url: url,               // 요청 URL
        method: options.method || HTTP_METHODS.GET,  // HTTP 메서드 (기본값은 GET)
        dataType: 'json',       // 응답 데이터 타입
        headers: options.headers || {},  // 기본 헤더 설정
        data: options.data || {},       // 요청 데이터
    };

    // AJAX 요청을 Promise로 래핑하여 반환
    return new Promise((resolve, reject) => {
        $.ajax({
            ...settings,
            success: function(data) {
                resolve(data); // 요청이 성공하면 데이터를 resolve
            },
            error: function(xhr, status, error) {
                console.error('There was a problem with your AJAX operation:', error);
                reject(new Error(error));  // 에러 발생 시 reject
            }
        });
    });
}
