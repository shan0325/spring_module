class Pagination {
    constructor(setting) {
        if (!setting) {
            throw new Error("The setting parameter is required.");
        }
        this.setting = setting;

        // 콜백 함수를 호출하는 이벤트 리스너를 등록
        document.addEventListener("click", function (e) {
            if (e.target.matches(setting.target + " .page-link")) {
                const pageNo = e.target.dataset.pageno;
                setting.clickCallback(pageNo);
            }
        });
    }

    makePaging(pagination) {
        if (!pagination || pagination.empty) return;

        const pageNumber = pagination.pageable.pageNumber;
        const pageSize = 10;
        const totalPages = pagination.totalPages;
        const startPage = Math.floor(pageNumber / pageSize) * pageSize + 1;
        const tempEndPage = startPage + pageSize - 1;
        const endPage = tempEndPage < totalPages ? tempEndPage : totalPages;
        const prevBlockPage = startPage === 1 ? 1 : startPage - 1;
        const nextBlockPage = endPage === totalPages ? endPage : endPage + 1;

        let pageHtml = "";
        pageHtml += `
            <li class="page-item">
                <a href="javascript:;" class="page-link ${pagination.first ? 'disabled' : ''}" data-pageno="0">&lt;&lt;</a>
            </li>
        `;
        pageHtml += `
            <li class="page-item">
                <a href="javascript:;" class="page-link ${pagination.first ? 'disabled' : ''}" data-pageno="${prevBlockPage - 1}">&lt;</a>
            </li>
        `;

        // 페이징 번호 표시
        for (let i = startPage; i <= endPage; i++) {
            if (pageNumber + 1 === i) {
                pageHtml += `
                    <li class="page-item active">
                        <a href="javascript:;" class="page-link" data-pageno="${i - 1}">${i}</a>
                    </li>
                `;
            } else {
                pageHtml += `
                    <li class="page-item">
                        <a href="javascript:;" class="page-link" data-pageno="${i - 1}">${i}</a>
                    </li>
                `;
            }
        }

        pageHtml += `
            <li class="page-item">
                <a href="javascript:;" class="page-link ${pagination.last ? 'disabled' : ''}" data-pageno="${nextBlockPage - 1}">&gt;</a>
            </li>
        `;
        pageHtml += `
            <li class="page-item">
                <a href="javascript:;" class="page-link ${pagination.last ? 'disabled' : ''}" data-pageno="${totalPages - 1}">&gt;&gt;</a>
            </li>
        `;

        document.querySelector(this.setting.target).innerHTML = pageHtml;
    }
}