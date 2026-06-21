(function ($) {
	"use strict";

    jQuery(document).ready(function ($) {
        
        $(".thumb-image").find('img').bind("click", function() {
            var src = $(this).attr("src");
            // Check the beginning of the src attribute  
            var state = (src.indexOf("bw_") === 0) ? 'bw' : 'clr';
            // Modify the src attribute based upon the state var we just set
            (state === 'bw') ? src = src.replace('bw_', 'clr_') : src = src.replace('clr_', 'bw_');
            // Apply the new src attribute value  
            $(this).attr("src", src);

            // This is just for demo visibility
            $('.thumb-main-image img').attr("src", src);
            
            $('.thumb-image li.active').removeClass('active');
            
            $(this).parent().parent().addClass('active');
            
            

          return false;
        });
        
        var spins = document.getElementsByClassName("qt-area");
        for (var i = 0, len = spins.length; i < len; i++) {
            var spin = spins[i],
                span = spin.getElementsByTagName("i"),
                input = spin.getElementsByTagName("input")[0];

            input.onchange = function() { input.value = +input.value || 0; };
            span[0].onclick = function() { input.value = Math.max(0, input.value - 1); };
            span[1].onclick = function() { input.value -= -1; };
        }

        // 장바구니 담기 버튼 클릭 시 AJAX로 백엔드 호출
        $(document).on('click', '.cart-add-btn', function() {
            var productSeq = $(this).data('product-seq');
            var productName = $(this).data('product-name');
            var productPrice = $(this).data('product-price');
            $.ajax({
                url: '/cart/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ productSeq: productSeq, quantity: 1 }),
                success: function(result) {
                    if (!result || result.success !== true) {
                        alert(result && result.message === 'LOGIN_REQUIRED' ? '로그인 후 장바구니를 이용할 수 있습니다.' : '장바구니 담기 실패');
                        if (result && result.message === 'LOGIN_REQUIRED') window.location.href = '/login';
                        return;
                    }
                    updateCartSummary();
                },
                error: function(xhr) {
                    var message = xhr.responseJSON && xhr.responseJSON.message;
                    alert(message === 'LOGIN_REQUIRED' ? '로그인 후 장바구니를 이용할 수 있습니다.' : '장바구니 담기 실패');
                    if (xhr.status === 401 || message === 'LOGIN_REQUIRED') window.location.href = '/login';
                }
            });
        });

        // 페이지 로드 시 장바구니 정보 갱신
        updateCartSummary();


    });


    jQuery(window).load(function(){

        
    });


}(jQuery));

function updateCartSummary() {
    $.ajax({
        url: '/cart/list',
        type: 'GET',
        success: function(cartList) {
            if (!Array.isArray(cartList)) {
                $(".cart-text").html('장바구니<br>0 건 - 0원');
                return;
            }
            var totalCount = 0;
            var totalPrice = 0;
            for (var i = 0; i < cartList.length; i++) {
                var qty = parseInt(cartList[i].quantity) || 0;
                var price = parseInt(cartList[i].price) || 0;
                totalCount += qty;
                totalPrice += price * qty;
            }
            $(".cart-text").html('장바구니<br>' + totalCount + ' 건 - ' + totalPrice.toLocaleString() + '원');
        },
        error: function() {
            $(".cart-text").html('장바구니<br>0 건 - 0원');
        }
    });
}

function showCartToast(message) {
    var $toast = $('.dm-cart-toast');
    if (!$toast.length) {
        $toast = $('<div class="dm-cart-toast" role="status" aria-live="polite">'
            + '<div><strong></strong><span>상품 수량은 장바구니에서 변경할 수 있습니다.</span></div>'
            + '<a href="/cart.html">장바구니 보기</a>'
            + '</div>');
        $('body').append($toast);
    }
    $toast.find('strong').text(message || '장바구니에 담았습니다.');
    $toast.addClass('is-visible');
    clearTimeout(window.__dmCartToastTimer);
    window.__dmCartToastTimer = setTimeout(function () {
        $toast.removeClass('is-visible');
    }, 2600);
}
