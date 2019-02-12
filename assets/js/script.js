$(function(){
    function commonFunc(){
        $(document).on('click','.script-toggle-base-menu',function(e){
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

        $(document).on('click','.script-toggle-side-menu',function(e){
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

        $(document).on('click','.script-add-menu',function(e){
            e.preventDefault();
            if(!$(this).hasClass('active')) {
                $(this).addClass('active');
                $('.base-body').append('<div class="module-back-layer"></div>');
                $('.script-add-menu-popup').addClass('active');
            }
        });

        $(document).on('click','.script-edit-menu',function(e){
            e.preventDefault();
            if(!$(this).hasClass('active')) {
                $(this).addClass('active');
                $('.base-body').append('<div class="module-back-layer"></div>');
                $('.script-edit-menu-popup').addClass('active');
            }
        });

        $(document).on('click','.script-menu-delete',function(e) {
            e.preventDefault();
            if(!$(this).hasClass('active')){
                $(this).addClass('active');
                $('.base-body').append('<div class="module-back-layer"></div>');
                $('.script-menu-delete-popup').addClass('active');
            }
        });

        $(document).on('click','.script-page-delete',function(e){
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

        $(document).on('click','.script-user-name-mod',function(e){
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

        $(document).on('click','.script-user-password-mod',function(e){
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

        $(document).on('click','.script-user-delete',function(e){
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

        $(document).on('click','.script-user-add',function(e){
            e.preventDefault();
            if(!$(this).hasClass('active')) {
                $(this).addClass('active');
                $('.base-body').append('<div class="module-back-layer"></div>');
                $('.script-user-add-popup').addClass('active');
            }
        });

        $(document).on('click','.script-popup-cancel',function(e){
            e.preventDefault();
            backLayerCancel();
        });

        $(document).on('click','.module-back-layer',function(e){
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


        $(document).on('click','.script-add-page-show',function(e) {
            e.preventDefault();
            var target = $(this).parents('.menu-area').parents('div').next('ul').children('.add-under-page');
            target.addClass('active');
        });

        $(document).on('click','.script-add-page-hidden',function(e){
            e.preventDefault();
            $(this).parents('.add-under-page').removeClass('active');
        });

        $(document).on('click','.script-add-root-page-show',function(e){
            e.preventDefault();
            $('.module-add-root-page').addClass('active');
        });

        $(document).on('click','.script-add-root-page-hidden',function(e){
            e.preventDefault();
            $('.module-add-root-page').removeClass('active');
        });

        $(document).on('click','.script-user-menu-toggle .user-name',function(e){
            e.preventDefault();
            if(!$(this).parent().hasClass('active')) {
                $(this).parent().addClass('active')
            }else {
                $(this).parent().removeClass('active')
            }
        });


        $(document).on('click','.script-screen-reload',function(e){
            e.preventDefault();
            location.reload();
        });

        $(document).on('click','.script-page-sort-down',function(e){
          e.preventDefault();
          var targetId = $(this).parents(".menu-area").parents("li").next("li").attr("data-page-id");
          $(this).parents('form').find("[name='secondPageId']").val(targetId);
          $(this).parents('form').submit();
          return false;
        });

        $(document).on('click','.script-page-sort-up',function(e){
          e.preventDefault();
          var targetId = $(this).parents(".menu-area").parents("li").prev("li").attr("data-page-id");
          $(this).parents('form').find("[name='secondPageId']").val(targetId);
          $(this).parents('form').submit();
          return false;
        });


      $(document).on('click','.script-menu-sort-up',function(e){
        e.preventDefault();
        var activeMenu = $('.module-menu-lists > ul').find('a.active').parents('ul');
        var prevId = activeMenu.prev('ul').attr('data-id');
        $(this).parents('form').find('[name="secondMenuId"]').val(prevId);
        $(this).parents('form').submit();
      });
      $(document).on('click','.script-menu-sort-down',function(e){
        e.preventDefault();
        var activeMenu = $('.module-menu-lists > ul').find('a.active').parents('ul');
        var nextId = activeMenu.next('ul').attr('data-id');
        $(this).parents('form').find('[name="secondMenuId"]').val(nextId);
        $(this).parents('form').submit();
      });

    }

    var init = function () {
        events();
        Barba.Pjax.start();
        commonFunc();
    };

    var events = function () {
        Barba.Dispatcher.on('linkClicked', function(HTMLElement, MouseEvent) {
            document.body.classList.add('is-transition-start');
        });
        Barba.Dispatcher.on('transitionCompleted', function(HTMLElement, MouseEvent) {
            document.body.classList.remove('is-transition-start');
        });
    };

    init();

});