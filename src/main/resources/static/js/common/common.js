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
                const statusCode = xhr.status;
                switch (statusCode) {
                    case 401:
                        console.error('Unauthorized - 401:', xhr.responseText);
                        reject(new Error('Unauthorized: ' + xhr.responseText));
                        break;
                    case 403:
                        console.error('Forbidden - 403:', xhr.responseText);
                        reject(new Error('Forbidden: ' + xhr.responseText));
                        break;
                    case 404:
                        console.error('Not Found - 404:', xhr.responseText);
                        reject(new Error('Not Found: ' + xhr.responseText));
                        break;
                    case 500:
                        console.error('Internal Server Error - 500:', xhr.responseText);
                        reject(new Error('Internal Server Error: ' + xhr.responseText));
                        break;
                    default:
                        reject(xhr.responseText);
                        break;
                }
            }
        });
    });
}
