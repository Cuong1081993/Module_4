// Khi click vào để mở edit

function makeEdit() {
    document.querySelector(".model-container").classList.add('show');

}

// Đóng nút x
function closeAdd() {
    document.querySelector(".model-container").classList.remove('show');
  
}
// Cancel khi bung màn add lên

function makeCancel() {
    document.querySelector(".model-container").classList.remove('show');
 
}

// make delete
function makeDelete(){
    document.querySelector(".model-delete").classList.add('show');
   
}
function closeDelete(){
    document.querySelector(".model-delete").classList.remove("show");

}