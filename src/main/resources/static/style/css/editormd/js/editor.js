var testEditor;
$(function () {
    testEditor = editormd("my-editormd", {
        width: "98%",
        height: 600,
        syncScrolling: "single",
        path: "style/css/editormd/lib/", //依赖lib文件夹路径
        emoji: true,
        taskList: true,
        tocm: true,
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL: "/uploadImage",		//上传图片控制器Mapping
        saveHTMLToTextarea: true //注意3：这个配置，方便post提交表单
    });
});

var publishBtn = $(".publishBtn");
var qzEditorTitle = $("#qz-editor-title");
var myEditormdHtmlCode = $("#my-editormd-html-code");
var noticeBoxTitle = $(".notice-box-title");
var noticeBoxContent = $(".notice-box-content");
var noticeBox = $('.notice-box');
/**
 * 发布博客
 */
publishBtn.click(function (data) {
    var qzEditorTitleValues = qzEditorTitle.val();
    var myEditormdHtmlCodeValues = myEditormdHtmlCode.val();
    if (qzEditorTitleValues.length == 0) {
        noticeBoxTitle.show();
    } else if (myEditormdHtmlCodeValues.length == 0) {
        noticeBoxContent.show();
    } else {
        $('#my-alert').modal();
    }
    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);
});

// 插入标签
var addTagsBtn = $('.addTagsBtn');
$(function () {
    var i = 0;
    var $tag = $(".tag");
    var appendParam = function () {
        var param = $('<div style="display: inline-block;"><p class="tag-name" contenteditable="true"></p>' +
            '<i class="am-icon-times removeTag" style="color: #CCCCCC"></i></div>');
        $tag.append(param);
        $('.tag-name').click(function () {
            $(this).focus();
        });
    };
    addTagsBtn.on("click", function () {
        if (i >= 4) {
            addTagsBtn.attr('disabled', 'disabled');
        }
        var value = $(".tag-name").eq(i - 1).html();
        if (value != "") {
            appendParam();
            i++;
            console.log(i);
        }
    });

    $tag.on("click", ".removeTag", function () {
        $(this).parent().remove();
        i--;
        console.log(45);
        if (i <= 4) {
            addTagsBtn.removeAttr("disabled");
        }
        console.log(i);
    });

})


/*
显示文章原作者
 */
var selectType = $("#select-type");
selectType.blur(function () {
    var slectValue = selectType.val();
    if (slectValue == "原创") {
        $("#originalAuthorHide").hide();
    } else if (slectValue == "转载") {
        $("#originalAuthorHide").show();
    }
});

/*
发表博客
 */
var selectCategories = $("#select-categories");
var selectGrade = $("#select-grade");
var originalAuthor = $(".originalAuthor");
var message = $(".url");
var surePublishBtn = $(".surePublishBtn");
surePublishBtn.click(function () {
    /**
     * 标签
     * @type {jQuery}
     */
    var length = $(".tag").find(".tag-name").length;
    var tagValues = [];
    for (var i = 0; i < length; i++) {
        tagValues[i] = $(".tag-name").eq(i).html();
    }

    /**
     *  文章类型
     */
    var selectTypes = selectType.val();

    /**
     * 博客分类
     */
    var selectCategorie = selectCategories.val();

    /**
     * 文章等级
     */
    var selectGrades = selectGrade.val();

    /**
     * 原作者
     */
    var originalAuthors = originalAuthor.val();

    /**
     * 文章（0-公开  1-私密）
     */
    var messages = $("input[type='radio']:checked").val();

    /**
     * 标题
     */
    var title = qzEditorTitle.val();

    console.log(title);

    if (length <= 0 || tagValues[length - 1] == "") {
        $(".notice-box-tags").show();
    } else if (selectType.val() == "choose") {
        $(".notice-box-type").show();
    } else if (selectCategories.val() == "choose") {
        $(".notice-box-categories").show();
    } else if (selectGrade.val() == "choose") {
        $(".notice-box-grade").show();
    } else if (selectType.val() == "转载" && originalAuthor.val() == "") {
        $(".notice-box-originalAuthor").show();
    } else {
        var data = {
            tagValue: tagValues,
            selectType: selectTypes,
            selectCategories: selectCategorie,
            selectGrade: selectGrades,
            originalAuthor: originalAuthors,
            message: messages,
            title: title,
            text: myEditormdHtmlCode.val(),
            articleHtmlContent: testEditor.getHTML()
        };
        console.log(data);
        $.ajax({
            type: "POST",
            url: "/publishEditor",
            traditional: true,// 传数组
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function (data) {
                console.log(data);
            }
        })
    }

    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);

})