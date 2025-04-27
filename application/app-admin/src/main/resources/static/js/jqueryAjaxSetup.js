/**
 * 모든 AJAX 요청에 대한 기본 설정을 셋팅
 */
(function($) {
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader("IS_AJAX", "Y");
        },
        error: function(xhr, status, error) {
            const responseJSON = xhr.responseJSON;
            if (responseJSON && responseJSON.exceptionResponse) {
                const exceptionResponse = responseJSON.exceptionResponse;
                alert(exceptionResponse.message);

                if (exceptionResponse.redirectUrl) {
                    location.href = exceptionResponse.redirectUrl;
                }
            } else {
                alert("시스템 오류가 발생하였습니다. 다시 시도해주세요");
            }
        }
    });
})(jQuery);