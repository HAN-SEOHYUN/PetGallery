class Pagination {
    constructor(data) {
        this.content = data.content; // 데이터 항목
        this.pageable = data.pageable; // 페이지네이션 정보
        this.totalPages = data.totalPages; // 전체 페이지 수
        this.totalElements = data.totalElements; // 전체 아이템 수
        this.currentPage = data.pageable.pageNumber; // 현재 페이지
        this.pageSize = data.pageable.pageSize; // 한 페이지의 크기
    }

    get startIndex() {
        return this.currentPage * this.pageSize;
    }

    get endIndex() {
        return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);
    }

    get pageRange() {
        const range = [];
        for (let i = 0; i < this.totalPages; i++) {
            range.push(i + 1); // 1부터 시작하는 페이지 번호
        }
        return range;
    }

    setCurrentPage(page) {
        if (page < 0 || page >= this.totalPages) {
            throw new Error('Invalid page number');
        }
        this.currentPage = page;
    }

    hasNext() {
        return this.currentPage < this.totalPages - 1;
    }

    hasPrevious() {
        return this.currentPage > 0;
    }
}
