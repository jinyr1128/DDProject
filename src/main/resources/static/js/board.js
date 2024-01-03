// 모달을 여는 함수, 특정 카드에 대응하는 HTML 파일을 엽니다.
function openModal(cardUrl) {
    var modal = document.getElementById("myModal");
    var iframe = modal.querySelector("iframe");
    iframe.src = cardUrl; // 클릭된 카드에 해당하는 URL로 iframe의 src를 설정합니다.
    modal.style.display = "block";
}

// 모달을 닫는 함수
function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
    modal.querySelector("iframe").src = ""; // 모달이 닫힐 때 iframe의 src를 비워줍니다.
}

// 모달 바깥 영역 클릭시 모달을 닫습니다.
window.onclick = function(event) {
    var modal = document.getElementById("myModal");
    if (event.target == modal) {
        closeModal();
    }
}
// 새로운 카드를 추가하는 함수
function addNewCard(columnElement) {
    // 새로운 카드 요소 생성
    var newCard = document.createElement("div");
    newCard.classList.add("card");
    newCard.setAttribute("onclick", "openModal('새로운 카드 정보')"); // 임시 정보

    // 새로운 카드 내용 설정
    var newCardContent = document.createElement("div");
    newCardContent.classList.add("task-header");
    newCardContent.textContent = "새로운 카드"; // 임시 텍스트

    // 카드에 내용 추가
    newCard.appendChild(newCardContent);

    // 카드를 칼럼에 추가
    columnElement.insertBefore(newCard, columnElement.lastElementChild);
}
function addNewColumn() {
    // 새로운 칼럼 요소 생성
    var newColumn = document.createElement("div");
    newColumn.classList.add("column");

    // 칼럼 헤더 추가
    var columnHeader = document.createElement("div");
    columnHeader.classList.add("column-header");
    columnHeader.textContent = "새 칼럼"; // 임시 텍스트
    newColumn.appendChild(columnHeader);

    // 새로운 카드 추가 버튼 추가
    var newCardButton = document.createElement("div");
    newCardButton.classList.add("task-new");
    newCardButton.textContent = "+ 새로운 카드 추가";
    newCardButton.onclick = function() {
        addNewCard(newColumn);
    };
    newColumn.appendChild(newCardButton);

    // 보드에 새로운 칼럼 추가
    var board = document.querySelector(".board");
    board.insertBefore(newColumn, board.lastElementChild);
}
document.addEventListener('DOMContentLoaded', () => {
    const cards = document.querySelectorAll('.card');
    const columns = document.querySelectorAll('.column');

    // 카드 드래그 앤 드롭 이벤트 설정
    cards.forEach(card => {
        card.addEventListener('dragstart', handleDragStart);
        card.addEventListener('dragend', handleDragEnd);
    });

    // 칼럼 드래그 앤 드롭 이벤트 설정
    columns.forEach(column => {
        column.addEventListener('dragover', handleDragOver);
        column.addEventListener('drop', handleDrop);
    });
});

function handleDragStart(e) {
    e.dataTransfer.setData('text/plain', e.target.dataset.cardId || e.target.parentElement.dataset.columnId);
    this.classList.add('dragging');
}

function handleDragEnd() {
    this.classList.remove('dragging');
}

function handleDragOver(e) {
    e.preventDefault();
}

function handleDrop(e) {
    e.preventDefault();
    const draggedId = e.dataTransfer.getData('text/plain');
    const draggedElement = document.querySelector(`[data-card-id="${draggedId}"], [data-column-id="${draggedId}"]`);

    if (draggedElement.classList.contains('card')) {
        // 카드 드롭 로직
        const dropZone = e.target.closest('.column');
        if (dropZone) {
            dropZone.appendChild(draggedElement);
        }
    } else if (draggedElement.classList.contains('column')) {
        // 칼럼 드롭 로직
        const board = document.querySelector('.board');
        board.insertBefore(draggedElement, e.target.closest('.column').nextSibling);
    }
}
//function updatePositionOnServer(draggedId, newColumnId, newPosition) {
//     // AJAX 요청을 사용하여 서버에 위치 변경 정보를 전송
//     // 예시: axios.post('/api/updatePosition', { draggedId, newColumnId, newPosition });
// }

