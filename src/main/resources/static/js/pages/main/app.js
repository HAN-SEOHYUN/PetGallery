const REQUEST_URL = '/api/likes/rank';
const rankIcons = ["🥇", "🥈", "🥉", "🎖️", "🏅"];

$(document).ready(function () {
    fetchLikeRanking();
});

function fetchLikeRanking() {
    showLoading();

    fetch(REQUEST_URL, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => response.json())
        .then(handleLikeRankingSuccess)
        .catch(handleError)
        .finally(hideLoading);
}

// ✅ 로딩 UI 표시
function showLoading() {
    const $section = $(".ranking-section");
    $section.show(); // 로딩 중에는 섹션 먼저 보이도록
    $("#ranking-loading").show();
    $("#like-ranking-list").hide();
}

// ✅ 로딩 UI 숨기기
function hideLoading() {
    $("#ranking-loading").hide();
    $("#like-ranking-list").show();
}

// ✅ 성공 응답 처리
function handleLikeRankingSuccess(response) {
    const data = response.data || [];
    const $section = $(".ranking-section");
    const $list = $("#like-ranking-list");

    clearRankingList($list);

    if (data.length === 0) {
        $section.hide(); // 데이터 없으면 전체 숨김
        return;
    }

    $section.fadeIn(); // 데이터 있으면 보여줌
    renderRankingList($list, data);
}

// ✅ 실패 처리 (필요 시 사용자 알림 등 추가 가능)
function handleError(error) {
    console.error('랭킹 데이터를 불러오는 중 오류 발생:', error);
    $(".ranking-section").hide(); // 에러 시에도 숨김 처리
}

// ✅ 리스트 초기화
function clearRankingList($container) {
    $container.empty();
}

// ✅ 리스트 렌더링
function renderRankingList($container, data) {
    data.slice(0, 5).forEach((item, index) => {
        const icon = rankIcons[index] || "";
        const listItem = createRankingListItem(index + 1, icon, item.name, item.likeCount ?? "N");
        $container.append(listItem);
    });
}

// ✅ 리스트 아이템 생성
function createRankingListItem(rank, icon, name, likeCount) {
    return `
        <li class="ranking-item">
            <span class="rank-number">${icon} </span>
            <span class="pet-name">${name}</span>
            <span class="likes"><i class="fas fa-heart"></i> ${likeCount}</span>
        </li>
    `;
}