
// 弹窗
function showWindow() {
    $('#showdiv').show();   //显示弹窗
    $('.content').append();    //追加内容
    $('#cover').css('display', 'block'); //显示遮罩层

}

// 关闭弹窗
function closeWindow() {
    $('#showdiv').hide();   //隐藏弹窗
    $('#cover').css('display', 'none');     //显示遮罩层
    $('#showdiv .content').html("");    //清空追加的内容
}
$(function () {
    $('#myTab a:eq(0)').tab('show');
});
