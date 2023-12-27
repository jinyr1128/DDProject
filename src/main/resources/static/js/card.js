function addComment() {
    var commentBox = document.querySelector('.comments textarea');
    var commentSection = document.querySelector('.comment-section');

    // 새로운 댓글 div 생성
    var newComment = document.createElement('div');
    newComment.textContent = commentBox.value; // 텍스트 영역의 값으로 설정
    newComment.classList.add('comment');

    // 댓글 섹션에 새 댓글 추가
    commentSection.appendChild(newComment);

    // 입력된 텍스트 초기화
    commentBox.value = '';
}
function inviteUser() {
    // 초대 UI 로직을 구현하거나,
    // 모달을 표시하여 유저를 선택할 수 있게 합니다.
    // 예제에서는 console에 메시지를 출력하는 것으로 대체합니다.
    console.log("유저 초대 UI를 여는 로직이 여기에 들어갑니다.");
}
