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

/**
 * 파일 업로드를 처리 함수
 *
 * @param {string} url - 파일 업로드를 처리할 API 엔드포인트 URL
 * @param {Object} [options={}] - 함수 옵션 객체
 * @param {File} options.file - 업로드할 파일 객체
 * @param {Function} [options.successCallback] - 업로드 성공 시 호출되는 콜백 함수
 * @param {Function} [options.errorCallback] - 업로드 실패 시 호출되는 콜백 함수
 *
 * @returns {void}
 *
 * @example
 * // 사용 예시
 * const file = document.querySelector('#fileInput').files[0];
 * uploadFile('/api/upload', {
 *     file: file,
 *     successCallback: function(response) {
 *         console.log('업로드 성공:', response);
 *     },
 *     errorCallback: function(xhr, status, error) {
 *         console.error('업로드 실패:', error);
 *     }
 * });
 */
function uploadFile(url, options = {}) {
    const { file, successCallback, errorCallback } = options;

    if (!validateFile(file)) {return;}

    const formData = new FormData();
    formData.append('file', file);

    $.ajax({
        url: url,
        type: HTTP_METHODS.POST,
        data: formData,
        processData: false,
        contentType: false,
        headers: {
            'Accept': 'application/json'  // 'Accept' 헤더를 추가하여 응답 형식으로 JSON을 요청
        },
        success: successCallback,
        error: errorCallback
    });
}

function validateFile(file) {
    if (!file) {
        console.error('파일 없음');
        return false;
    }
    return true;
}

