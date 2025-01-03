/**
 * 버튼 1개인 알림을 표시하는 함수입니다.
 *
 * @param {Object} params - 알림을 표시할 때 필요한 파라미터입니다.
 * @param {string} params.title - 큰 문구 (예: "저장하시겠습니까?")
 * @param {string} params.text - 작은 문구 (예: "저장하신 후 수정이 불가합니다.")
 * @param {string} [params.alertType='info'] - 알림 타입 ('success', 'error', 'warning', 'info') - 기본값은 'info'
 *
 * @example
 * showOneButtonAlert({
 *     title: '등록이 완료되었습니다',
 *     text: '이 작업은 되돌릴 수 없습니다.',
 *     alertType: 'info', // 주의 알림
 *     callback: () => {
 *         console.log('등록 버튼 클릭됨');
 *     }
 * });
 */
function showOneButtonAlert({
                                title,  // 큰 문구
                                text,   // 작은 문구
                                alertType = 'info', // 알림 타입 (success, error, warning, info)
                                callback // 콜백 함수
                            }) {
    const buttonSettings = {
        warning: {color: 'btn-danger', text: '확인'},
        success: {color: 'btn-success', text: '확인'},
        error: {color: 'btn-danger', text: '확인'},
        info: {color: 'btn-secondary', text: '확인'},
    };

    const {color: buttonColor, text: buttonText} = buttonSettings[alertType] || buttonSettings.info;

    // SweetAlert2 호출
    Swal.fire({
        title: title,  // 큰 문구
        text: text,    // 작은 문구
        icon: alertType,  // 알림 타입 아이콘
        showCloseButton: true, // 닫기 버튼 표시
        position: 'center', // 알림 위치
        timer: 5000, // 알림 자동 종료 시간 (ms)
        showConfirmButton: true, // 확인 버튼 표시
        confirmButtonText: buttonText, // 버튼 텍스트
        confirmButtonColor: buttonColor, // 버튼 색상
        allowOutsideClick: false, // 바깥 클릭 시 알림 닫지 않도록 설정
    }).then((result) => {
        if (callback && typeof callback === 'function') {
            callback(result); // 콜백 함수 호출
        }
    });
}

/**
 * 버튼 2개인 알림을 표시하는 함수입니다.
 *
 * @param {Object} params - 알림을 표시할 때 필요한 파라미터입니다.
 * @param {string} params.title - 큰 문구 (예: "저장하시겠습니까?")
 * @param {string} params.text - 작은 문구 (예: "저장하신 후 수정이 불가합니다.")
 * @param {string} [params.alertType='info'] - 알림 타입 ('success', 'error', 'warning', 'info') - 기본값은 'info'
 * @param {function} [params.callback] - 알림을 닫은 후 실행할 콜백 함수
 *
 * @example
 * showTwoButtonAlert({
 *     title: '작업이 성공적으로 완료되었습니다!',
 *     text: '모든 데이터가 정상적으로 처리되었습니다.',
 *     alertType: 'success', // 성공 알림
 *     callback: (result) => {
 *         if (result.isConfirmed) {
 *             console.log('등록하기 버튼 클릭됨');
 *         } else {
 *             console.log('취소하기 버튼 클릭됨');
 *         }
 *     }
 * });
 */
function showTwoButtonAlert({
                                title,  // 큰 문구
                                text,   // 작은 문구
                                alertType = 'info', // 알림 타입 (success, error, warning, info)
                                callback // 콜백 함수
                            }) {
    const buttonSettings = {
        warning: {confirmColor: 'btn-primary', cancelColor: 'btn-danger', confirmText: '등록하기', cancelText: '취소하기'},
        success: {confirmColor: 'btn-primary', cancelColor: 'btn-secondary', confirmText: '등록하기', cancelText: '취소하기'},
        error: {confirmColor: 'btn-primary', cancelColor: 'btn-danger', confirmText: '등록하기', cancelText: '취소하기'},
        info: {confirmColor: 'btn-primary', cancelColor: 'btn-secondary', confirmText: '확인', cancelText: '취소'},
    };

    const {confirmColor, cancelColor, confirmText, cancelText} = buttonSettings[alertType] || buttonSettings.info;

    Swal.fire({
        title: title,  // 큰 문구
        text: text,    // 작은 문구
        icon: alertType,  // 알림 타입 아이콘
        showCloseButton: true, // 닫기 버튼 표시
        position: 'center', // 알림 위치
        timer: 5000, // 알림 자동 종료 시간 (ms)
        showConfirmButton: true, // 등록하기 버튼 표시
        confirmButtonText: confirmText, // 등록하기 버튼 텍스트
        confirmButtonColor: confirmColor, // 등록하기 버튼 색상
        showCancelButton: true, // 취소하기 버튼 표시
        cancelButtonText: cancelText, // 취소하기 버튼 텍스트
        cancelButtonClass: cancelColor, // 취소하기 버튼 색상
        allowOutsideClick: false, // 바깥 클릭 시 알림 닫지 않도록 설정
    }).then((result) => {
        if (result.isConfirmed && callback && typeof callback === 'function') {
            callback(result); // 확인 버튼 클릭 시 콜백 호출
        }
    });
}
