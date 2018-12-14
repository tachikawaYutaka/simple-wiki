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



});