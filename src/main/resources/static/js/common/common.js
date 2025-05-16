function fetchData(url, options = {}) {
    const settings = {
        url: url,               // 요청 URL
        method: options.method || HTTP_METHODS.GET,  // 기본값은 GET
        dataType: 'json',       // 응답 데이터 타입
        headers: options.headers || {},  // 기본 헤더 설정
    };

    if (options.method !== HTTP_METHODS.DELETE) {
        settings.data = options.data || {};
    }

    return new Promise((resolve, reject) => {
        $.ajax({
            ...settings,
            type: settings.method,
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
 * 비동기적으로 파일을 업로드하는 함수.
 * 서버에 파일을 업로드하고, 서버의 응답 데이터를 Promise로 반환.
 *
 * @param {string} url - 파일 업로드 API 엔드포인트.
 * @param {Object} options - 파일 업로드에 필요한 옵션 객체.
 * @param {File} options.file - 업로드할 파일 객체.
 * @returns {Promise<Object>} - 서버 응답 데이터를 포함한 Promise를 반환.
 *
 * @throws {Error} - 파일 유효성 검사가 실패하거나 업로드 요청이 실패할 경우 에러를 반환.
 *
 * @example
 * uploadFile('/api/upload', { file: myFile })
 *   .then((response) => {
 *     console.log('업로드 성공:', response);
 *   })
 *   .catch((error) => {
 *     console.error('업로드 실패:', error.message);
 *   });
 */
function uploadFile(url, options = {}) {
    const { file} = options;

    if (!validateFile(file)) { return Promise.reject(new Error('유효하지 않은 파일입니다.')); }

    const formData = new FormData();
    formData.append('file', file);

    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            type: HTTP_METHODS.POST,
            data: formData,
            processData: false,
            contentType: false,
            headers: {
                'Accept': 'application/json'
            },
            success: (response) => {
                resolve(response);
            },
            error: (xhr, status, error) => {
                reject(xhr.responseText);
            }
        });
    });
}

function validateFile(file) {
    if (!file) {
        return false;
    }
    return true;
}

$(document).ready(function () {
    $('#logoutBtn').on('click', function (e) {
        e.preventDefault();
        logoutUser();
    });
});

function logoutUser() {
    fetch("/api/logout", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) throw new Error('로그아웃 실패');
            return response.json(); // 필요 시
        })
        .then(() => {
            showOneButtonAlert({
                title: '로그아웃 되었습니다.',
                alertType: 'success',
                callback() {
                    window.location.href = '/main';
                }
            });
        })
        .catch(() => {
            showOneButtonAlert({
                title: '로그아웃에 실패했습니다',
                alertType: 'error',
                callback() {}
            });
        });
}
