const SC_COMMON = {
    // 숫자 3자리마다 콤마 찍기
    formatNumberWithCommas(number) {
        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },

    // 이메일 유효성 검사
    isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    },

    // 깊은복사(Deep Copy)
    deepCopy(obj) {
        return JSON.parse(JSON.stringify(obj));
    },

    // 핸드폰번호 체크
    phoneNumberCheck(number){
        let result = /^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
        return result.test(number);
    },

    removeURLParameter(url, parameter) {
        const urlObj = new URL(url);
        const params = urlObj.searchParams;
        params.delete(parameter);
        return urlObj.toString();
    }

}
