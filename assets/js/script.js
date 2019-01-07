$(function(){
    $('.script-toggle-base-menu').on('click',function(e){
        e.preventDefault();
        if(!$(this).hasClass('active')) {
            $(this).addClass('active');
            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.base-menu').addClass('active');
            $('.base-body').addClass('overflow-hidden');
        }else {
            $(this).removeClass('active');
            $('.base-menu').removeClass('active');
            $('.base-body').removeClass('overflow-hidden');
            $('.module-back-layer').remove();
        }
    });

    $('.script-toggle-side-menu').on('click',function(e){
        if(!$(this).hasClass('active')) {
            var height = $('.base-content-body-head').outerHeight();
            $('.base-content-side').css('top',height + 60);
            $('.base-content-side').css('padding-bottom',height + 60 + 100);
            $('.base-body').append('<div class="module-back-layer"></div>');
            $(this).addClass('active');
            $('.base-content-side').addClass('active');
             $('.base-body').addClass('overflow-hidden');
        }else {
            $('.script-toggle-side-menu').removeClass('active');
            $('.base-body').removeClass('overflow-hidden');
            $('.base-content-side').removeClass('active');
            $('.module-back-layer').remove();
            setTimeout(function(){
                $('.base-content-side').css('top','auto');
                $('.base-content-side').css('padding-bottom',0);
            },100);
        }
    });

    $('.script-add-menu').on('click',function(e){
        e.preventDefault();
        if(!$(this).hasClass('active')) {
            $(this).addClass('active');
            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.script-add-menu-popup').addClass('active');
        }
    });

    $('.script-edit-menu').on('click',function(e){
        e.preventDefault();
        if(!$(this).hasClass('active')) {
            $(this).addClass('active');
            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.script-edit-menu-popup').addClass('active');
        }
    });

    $('.script-menu-delete').on('click',function(e) {
        e.preventDefault();
        if(!$(this).hasClass('active')){
            $(this).addClass('active');
            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.script-menu-delete-popup').addClass('active');
        }
    });

    $('.script-page-delete').on('click',function(e){
        e.preventDefault();
        if(!$(this).hasClass('active')){
            $(this).addClass('active');
            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.script-page-delete-popup').addClass('active');
            var name = $(this).attr('data-page-name');
            var menuId = $(this).attr('data-menu-id');
            var pageId = $(this).attr('data-page-id');
            var action = $('.script-page-delete-popup').find('form').attr('data-action');
            $('.script-page-delete-popup').find('form').attr('action',action.replace("##MENUID##",menuId).replace("##PAGEID##",pageId));
            $('.script-page-delete-popup').find('.page-name').text(name);
        }
    });

    $('.script-user-name-mod').on('click',function(e){
        e.preventDefault();
        if(!$(this).hasClass('active')) {
            $(this).addClass('active');
            var userId = $(this).parents('.menu').parents('li').attr('data-user-id');
            var userName = $(this).parents('.menu').parents('li').attr('data-user-name');
            var action = $('.script-user-name-mod-popup').find('form').attr('data-action');
            $('.script-user-name-mod-popup form').attr('action',action.replace("##USERID##",userId));
            $('.script-user-name-mod-popup form [name="userName"]').val(userName);

            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.script-user-name-mod-popup').addClass('active');
        }
    });

    $('.script-user-password-mod').on('click',function(e){
        e.preventDefault();
        if(!$(this).hasClass('active')) {
            $(this).addClass('active');
            var userId = $(this).parents('.menu').parents('li').attr('data-user-id');
            var action = $('.script-user-password-mod-popup').find('form').attr('data-action');
            $('.script-user-password-mod-popup form').attr('action',action.replace("##USERID##",userId));

            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.script-user-password-mod-popup').addClass('active');

        }
    });

    $('.script-user-delete').on('click',function(e){
        e.preventDefault();
        if(!$(this).hasClass('active')) {
            $(this).addClass('active');
            var userId = $(this).parents('.menu').parents('li').attr('data-user-id');
            var userName = $(this).parents('.menu').parents('li').attr('data-user-name');
            var action = $('.script-user-delete-popup').find('form').attr('data-action');
            $('.script-user-delete-popup form').attr('action',action.replace("##USERID##",userId));
            $('.script-user-delete-popup form .user-name').text(userName);

            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.script-user-delete-popup').addClass('active');
        }
    });

    $('.script-user-add').on('click',function(e){
        e.preventDefault();
        if(!$(this).hasClass('active')) {
            $(this).addClass('active');
            $('.base-body').append('<div class="module-back-layer"></div>');
            $('.script-user-add-popup').addClass('active');
        }
    });

    $('.script-popup-cancel').on('click',function(e){
        e.preventDefault();
        backLayerCancel();
    });

    $('.base-body').on('click','.module-back-layer',function(e){
        e.preventDefault();
        backLayerCancel();
    });

    function backLayerCancel(){
        $('.script-page-delete').removeClass('active');
        $('.script-page-delete-popup').removeClass('active');

        $('.script-menu-delete').removeClass('active');
        $('.script-menu-delete-popup').removeClass('active');

        $('.script-toggle-base-menu').removeClass('active');
        $('.base-menu').removeClass('active');
        $('.base-body').removeClass('overflow-hidden');

        $('.script-toggle-side-menu').removeClass('active');
        $('.base-content-side').removeClass('active');

        $('.script-add-menu').removeClass('active');
        $('.script-add-menu-popup').removeClass('active');

        $('.script-edit-menu').removeClass('active');
        $('.script-edit-menu-popup').removeClass('active');

        $('.script-user-name-mod').removeClass('active');
        $('.script-user-name-mod-popup').removeClass('active');

        $('.script-user-password-mod').removeClass('active');
        $('.script-user-password-mod-popup').removeClass('active');

        $('.script-user-delete').removeClass('active');
        $('.script-user-delete-popup').removeClass('active');

        $('.script-user-add').removeClass('active');
        $('.script-user-add-popup').removeClass('active');

        $('.module-back-layer').remove();

        setTimeout(function(){
            $('.base-content-side').css('top','auto');
            $('.base-content-side').css('padding-bottom',0);
        },100);
    }


    $('.script-add-page-show').on('click',function(e) {
        e.preventDefault();
        var target = $(this).parents('.menu-area').parents('div').next('ul').children('.add-under-page');
        target.addClass('active');
    });

    $('.script-add-page-hidden').on('click',function(e){
        e.preventDefault();
        $(this).parents('.add-under-page').removeClass('active');
    });

    $('.script-add-root-page-show').on('click',function(e){
        e.preventDefault();
        $('.module-add-root-page').addClass('active');
    });

    $('.script-add-root-page-hidden').on('click',function(e){
        e.preventDefault();
        $('.module-add-root-page').removeClass('active');
    });

    $('.script-user-menu-toggle').on('click', '.user-name',function(e){
        e.preventDefault();
        if(!$(this).parent().hasClass('active')) {
            $(this).parent().addClass('active')
        }else {
            $(this).parent().removeClass('active')
        }
    });


    $('.script-screen-reload').on('click',function(e){
        e.preventDefault();
        location.reload();
    });

});