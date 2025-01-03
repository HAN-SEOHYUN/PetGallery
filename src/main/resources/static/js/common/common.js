function fetchData(url, options = {}) {
    const settings = {
        url: url,               // 요청 URL
        method: options.method || HTTP_METHODS.GET,  // 기본값은 GET
        dataType: 'json',       // 응답 데이터 타입
        headers: options.headers || {},  // 기본 헤더 설정
        data: options.data || {},       // 요청 데이터
    };

    return new Promise((resolve, reject) => {
        $.ajax({
            ...settings,
            success: function(data) {
                resolve(data);
            },
            error: function(xhr, status, error) {
                console.error('요청 처리 중 문제가 발생했습니다.:', error);
                reject(new Error(error));
            }
        });
    });
}
