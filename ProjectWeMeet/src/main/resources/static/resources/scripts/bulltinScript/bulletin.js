const list = document.querySelector('.container');
const listScrollWidth = list.scrollWidth;
const listClientWidth = list.clientWidth;

const bulletinForm = document.getElementById('bulletinForm');
const commentContainer = document.getElementById('commentContainer');


let startX = 0;
let nowX = 0;
let endX = 0;
let listX = 0;
let isDragging = false;

const getClientX = (e) => {
    const isTouches = e.touches ? true : false;
    return isTouches ? e.touches[0].clientX : e.clientX;
};

const getTranslateX = () => {
    return parseInt(getComputedStyle(list).transform.split(/[^\-0-9]+/g)[5]);
};

const setTranslateX = (x) => {
    list.style.transform = `translateX(${x}px)`;
};

const onScrollStart = (e) => {
    startX = getClientX(e);
    isDragging = true;
    document.addEventListener('mousemove', onScrollMove);
    document.addEventListener('touchmove', onScrollMove);
    document.addEventListener('mouseup', onScrollEnd);
    document.addEventListener('touchend', onScrollEnd);
};

const onScrollMove = (e) => {
    if (!isDragging) return;
    nowX = getClientX(e);
    const v = listX + nowX - startX;
    if (v > 0) return;
    if (v < -(227 * list.childElementCount)) return;
    console.log(v);
    setTranslateX(v);
};

const bindEvents = () => {
    list.addEventListener('mousedown', onScrollStart);
    list.addEventListener('touchstart', onScrollStart);
};

const onScrollEnd = (e) => {
    if (!isDragging) return;
    isDragging = false;
    endX = getClientX(e);
    listX = getTranslateX();
    document.removeEventListener('mousemove', onScrollMove);
    document.removeEventListener('touchmove', onScrollMove);
    document.removeEventListener('mouseup', onScrollEnd);
    document.removeEventListener('touchend', onScrollEnd);


};

bindEvents();


const deleteButton = bulletinForm.querySelector('[name="delete"]');
const patchButton = bulletinForm.querySelector('[name="patch"]');

const finishButton = bulletinForm.querySelector('[name="finish"]');




//게시글 수정 및 삭제 참여 리뷰파트//게시글 수정 및 삭제 참여 리뷰파트//게시글 수정 및 삭제 참여 리뷰파트//게시글 수정 및 삭제 참여 리뷰파트//
// deleteButton.addEventListener('click', e => {
//     e.preventDefault();
//     const index = deleteButton.dataset.index;
//     const confirmResult = confirm('삭제하시겠습니까?');
//     if (confirmResult === true) {
//         const xhr = new XMLHttpRequest();
//         xhr.open('DELETE', `./read?index=${index}`);
//         xhr.onreadystatechange = () => {
//             if (xhr.readyState === XMLHttpRequest.DONE) {
//                 if (xhr.status >= 200 && xhr.status < 300) {
//                     const responseText = xhr.responseText;
//                     if (responseText === 'true') {
//                         alert('삭제되었습니다');
//                         location.href = '/article';
//                         return;
//                     } else {
//                         alert('작성한 사용자가 아니므로 삭제하지 못합니다');
//                         return;
//                     }
//                 } else {
//                     alert('서버와 통신하지 못하였습니다.\n\n잠시 후 다시 시도해 주세요.');
//                     return;
//                 }
//             }
//         };
//         xhr.send();
//     } else if (confirmResult === false) {
//         alert('삭제를 취소합니다');
//         return;
//     }
// }); //게시판 삭제

// patchButton.addEventListener('click', e => {
//     e.preventDefault();
//
//     const index = patchButton.dataset.index;
//
//     const xhr = new XMLHttpRequest();
//     xhr.open('GET', `./patch?index=${index}`);
//     xhr.onreadystatechange = () => {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             if (xhr.status >= 200 && xhr.status < 300) {
//                 const confirmResult = confirm('수정을 하시겠습니까?');
//                 if (confirmResult === true) {
//                     location.href = `/article/patch?index=${index}`
//                 } else if (confirmResult === false) {
//                     alert('수정을 취소합니다');
//                     return;
//                 }
//             } else {
//                 alert('작성한 사용자가 아니라 수정이 불가능합니다.');
//             }
//         }
//     };
//     xhr.send();
// }); //게시판 수정






//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//

// finishButton.addEventListener('click', e => {
//     e.preventDefault();
//     const index = finishButton.dataset.index;
//     const xhr = new XMLHttpRequest();
//     xhr.open('GET',`/article/review?index=${index}`);
//     xhr.onreadystatechange = () => {
//         if(xhr.readyState === XMLHttpRequest.DONE){
//             if(xhr.status >=200 && xhr.status<300){
//                 location.href = `/article/review?index=${index}`
//             }else{
//                 alert('작성한 사용자가 아니라 수정이 불가능합니다.');
//             }
//         }
//     };
//     xhr.send();
// })

//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//게시글 수정 리뷰파트//








// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트// 댓글파트//



//
function postComment(content, commentIndex, toFocus, refreshCommentAfter) {
    refreshCommentAfter ??= true;
    const articleIndex = bulletinForm['articleIndex'].value;
    const articleEmail = bulletinForm['articleEmail'].value;
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('articleIndex', articleIndex);
    formData.append('content', content);
    formData.append('articleEmail', articleEmail);
    formData.append('nickname',bulletinForm['nickname'].value);
    if (commentIndex) {
        formData.append('commentIndex', commentIndex);
    }
    xhr.open('POST', '/comment');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        alert('댓글을 작성하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                        break;
                    case 'failure_not_login':
                        alert('로그인 상태가 아닙니다. 로그인해주세요.');
                        break;
                    case 'success':
                    case 'success_same':
                        if (toFocus) {
                            toFocus.value = '';
                            toFocus.focus();
                        }
                        if (refreshCommentAfter === true) {
                            refreshComment();
                        }
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환하였습니다.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다.');
            }
        }
    };
    xhr.send(formData);
}

function refreshComment() {
    const articleIndex = bulletinForm['articleIndex'].value;
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/comment?articleIndex=${articleIndex}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const comments = JSON.parse(xhr.responseText);
                commentContainer.innerHTML = ''; // Clear existing comments

                for (const comment of comments) {
                    const div = document.createElement('div');
                    let commentClass = 'comment-left';

                    if (comment.same === true) {
                        commentClass = 'comment-right';
                    }
                    div.classList.add(commentClass);

                    const nicknameDiv = document.createElement('div');
                    nicknameDiv.classList.add('comment-nickname');
                    nicknameDiv.innerText = comment['nickname'];

                    const headDiv = document.createElement('div');
                    headDiv.classList.add('comment-head');


                    const bodyDiv = document.createElement('div');
                    bodyDiv.classList.add('comment-body');

                    const createdAt = new Date(comment['createdAt']); // Convert to Date object
                    const year = createdAt.getFullYear();
                    const month = String(createdAt.getMonth() + 1).padStart(2, '0');
                    const day = String(createdAt.getDate()).padStart(2, '0');
                    const hour = String(createdAt.getHours() % 12 || 12).padStart(2, '0');
                    const minute = String(createdAt.getMinutes()).padStart(2, '0');
                    const ampm = createdAt.getHours() < 12 ? '오전' : '오후';

                    headDiv.innerText = `${year}/${month}/${day} ${ampm} ${hour}:${minute}`;

                    const deleteButton = document.createElement('button');
                    deleteButton.classList.add('delete-button');
                    deleteButton.innerText = '삭제';
                    deleteButton.dataset.commentIndex = comment.index;
                    deleteButton.addEventListener('click', (e) => {
                        e.preventDefault();
                        if (!confirm('정말로 해당 댓글을 삭제할까요?')) {
                            return;
                        }
                        const xhr = new XMLHttpRequest();
                        const formData = new FormData();
                        xhr.open('DELETE', '/comment?index=' + e.target.dataset.commentIndex);
                        xhr.onreadystatechange = () => {
                            if (xhr.readyState === XMLHttpRequest.DONE) {
                                if (xhr.status >= 200 && xhr.status < 300) {
                                    const responseObject = JSON.parse(xhr.responseText);
                                    switch (responseObject.result) {
                                        case 'failure':
                                            alert('알 수 없는 이유로 댓글을 삭제하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
                                            break;
                                        case 'failure_deleted':
                                            alert('이미 삭제된 댓글 입니다. 새로고침 후 확인해 주세요.');
                                            break;
                                        case 'failure_no_authority':
                                            alert('삭제할 수 있는 권한이 없습니다.');
                                            break;
                                        case 'failure_not_login':
                                            alert('로그인 후 다시 시도해 주세요');
                                            break;
                                        case 'success':
                                            refreshComment();
                                            break;
                                        default:
                                            alert('서버가 알 수 없는 응답을 반환 했습니다.');
                                    }
                                } else {
                                    alert('서버와 통신할 수 없습니다.');
                                }
                            }
                        };
                        xhr.send(formData);
                    });

                    if (comment['deleted']) {
                        bodyDiv.innerText = '삭제된 댓글입니다.';
                        bodyDiv.style.color = '#a0a0a0';
                        bodyDiv.style.fontStyle = 'italic';
                        div.appendChild(bodyDiv);
                    } else {
                        bodyDiv.innerText = comment['content'];
                        const commentBox = document.createElement('div');
                        commentBox.classList.add('comment-box');

                        // Append headDiv, deleteButton, and bodyDiv as children of commentBox
                        commentBox.append(headDiv, deleteButton, bodyDiv);
                        div.append(nicknameDiv,commentBox);
                    }

                    commentContainer.appendChild(div);
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    };
    xhr.send();
}






// bulletinForm.onsubmit = function (e) {
//
//
//
//
//
// const writeButton = bulletinForm.querySelector('[name="write"]');
//
// finishButton.addEventListener('click', e => {
//     e.preventDefault();
//     const index = finishButton.dataset.index;
//     const confirmResult = confirm('게시물을 완료 하시겠습니까?');
//     if (confirmResult === true) {
//         const xhr = new XMLHttpRequest();
//         xhr.open('PATCH', `./review?index=${index}`);
//         xhr.onreadystatechange = () => {
//             if (xhr.readyState === XMLHttpRequest.DONE) {
//                 if (xhr.status >= 200 && xhr.status < 300) {
//                     const responseObject = JSON.parse(xhr.responseText);
//                     switch (responseObject.result) {
//                         case 'success':
//                             alert('게시물을 완료하였습니다.')
//                             const xhr = new XMLHttpRequest();
//                             xhr.open('GET', `./review?index=${index}`);
//                             xhr.onreadystatechange = () => {
//                                 if (xhr.readyState === XMLHttpRequest.DONE) {
//                                     if (xhr.status >= 200 && xhr.status < 300) {
//                                         location.href = `/article/review?index=${index}`
//                                     } else {
//                                         alert('사용자님의 게시물이 아니라 완료가 불가능합니다.');
//                                     }
//                                 }
//                             };
//                             xhr.send();
//                             break;
//                         case 'failure':
//                             alert('사용자님의 게시물이 아닙니다.');
//                             break;
//                         default:
//                             alert('default 값');
//                     }
//                 } else {
//                     alert('서버에서 문제가 생겼습니다.');
//                 }
//             }
//         };
//         xhr.send();
//     } else if (confirmResult === false) {
//         alert('완료를 취소합니다');
//         return;
//     }
//     postComment(bulletinForm['content'].value, undefined, bulletinForm['content']);
// });


// writeButton.addEventListener('click', e => {
//     e.preventDefault();
//     window.location.href = "/write";
// })


bulletinForm.onsubmit = function (e) {
    e.preventDefault();
    if (bulletinForm['content'].value == '') {
        alert('댓글을 입력해 주세요.');
        bulletinForm['content'].focus();
        return;
    }
    postComment(bulletinForm['content'].value, undefined, bulletinForm['content']);
}

document.addEventListener('DOMContentLoaded', () => {
    refreshComment();
});
