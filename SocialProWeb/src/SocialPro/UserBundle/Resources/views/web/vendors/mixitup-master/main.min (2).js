var site={platform:{ie:!1,noSvg:!1,touch:!1,noSvg:!1,init:function(){var a=this,b=(document.documentElement,navigator.userAgent.toLowerCase()),c=["webkit","moz"],d=window.SVGAngle?!0:!1,e=document.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#BasicStructure","1.1")?!0:!1,f=/gecko/i.test(b)&&/rv:1.9/i.test(b)?!0:!1,g=function(){for(var a,b=3,c=document.createElement("div"),d=c.getElementsByTagName("i");c.innerHTML="<!--[if gt IE "+ ++b+"]><i></i><![endif]-->",d[0];);return b>4?b:a}();a.touch="ontouchend"in document,a.ie=g||!1,a.ie=navigator.userAgent.indexOf("MSIE 10.0")>-1?10:a.ie,a.noSvg=!d||!e||9>g||f,a.ie&&a.ie<10;for(var h=0;h<c.length&&!window.requestAnimationFrame;++h)window.requestAnimationFrame=window[c[h]+"RequestAnimationFrame"];window.requestAnimationFrame||(window.requestAnimationFrame=function(a){window.setTimeout(a,1e3/60)})}}},imgLoaded=function(a,b,c){var d=a.parentElement,e=d.parentElement.parentElement,f=0,g=e.querySelectorAll(".slide").length;bg=null,show=function(){d.className+=d.className?" loaded":"loaded",c&&(f=e.querySelectorAll(".loaded").length,f===g&&(e.className+=e.className?" loaded":"loaded"))},b&&(bg=document.createElement("div"),bg.className="bg",bg.style.backgroundImage="url("+a.src+")",d.insertBefore(bg,a)),requestAnimationFrame(show)};site.platform.init(),function(a){a.extend(site,{$document:null,$window:null,$html:null,$body:null,ww:null,wh:null,scrollTop:null,init:function(){var b=this;b.$document=a(document),b.$window=a(window),b.$html=a("html"),b.$body=a("body"),b.$header=a("#GlobalHeader"),b.$scrollTo=a("a.scroll-to"),b.$sidebar=a("#Sidebar"),b.$configObjectGrid=a("#ConfigObjectGrid"),b.$apiMethodsGrid=a("#APIMethodsGrid"),b.$sandBox=a("#SandBox"),b.$sandBoxPagination=a("#SandBoxPagination"),b.$demo1=a("#Demo1"),b.$bbpForms=a("form.bbp-form"),b.$mixForms=a("form.mix-form"),b.$cart=a("#Cart"),b.$cartLink=a("#CartLink"),b.$addToCart=a("button.add-to-cart"),b.$downloadLinks=a("button.download-link"),b.$downloadIntercepts=a("a.download-intercept"),b.$subscribeLink=a("#ManageSubscription"),b.$shareLinks=a("#ShareLinks"),b.$topicsGrid=a("#TopicsGrid"),FastClick.attach(document.body),b.wh=site.$window.height(),b.ww=site.$window.width(),b.header.init(),b.backToTop.init(),b.modal.init(),b.checkHash(),b.loader.init(),b.$sandBox.length&&b.sandBox.init(),b.$demo1.length&&b.demo1.init(),b.$sidebar.length&&b.sidebar.init(),b.$configObjectGrid.length&&b.configObject.init(),b.$apiMethodsGrid.length&&b.apiMethods.init(),b.$sandBoxPagination.length&&b.sandBoxPagination.init(),b.$topicsGrid.length&&b.topicsGrid.init(),b.$bbpForms.each(function(){b.bbpForms.init(a(this))}),b.$mixForms.each(function(){b.mixForms.init(a(this))}),b.$cart.length&&b.cart.init(),b.$cartLink.length&&b.cartManager.init(),b.$addToCart.length&&b.addToCart.init(),b.$downloadLinks.length&&b.downloadLinks.init(),b.$subscribeLink.length&&b.manageSubscribe.init(),b.$shareLinks.length&&b.shareLinks.init(),b.$downloadIntercepts.length&&b.downloadIntercepts.init(),"undefined"!=typeof _gaq&&site.analytics.init(),b.globalHandlers(),b.stripe.init(),a("h2, h3, h4, h5, p").unBreak()},stripe:{testMode:mixitupAjax.stripeLive?!1:!0,live:"pk_live_3ERa9iP4hgvLB29p8otT1C8b",test:"pk_test_5vsXThLVtJlOoIxx3Iwrg9gB",init:function(){var a=this,b=a.testMode?a.test:a.live;Stripe.setPublishableKey(b)}},globalHandlers:function(){var a=this;a.$window.on({scroll:function(){a.scrollTop=a.$document.scrollTop(),a.$sidebar.length&&a.sidebar.$locked.length&&a.sidebar.lockArticles(),a.header.style(),a.backToTop.hideShow()},resize:function(){a.header.height=a.$header.height(),a.wh=site.$window.height(),a.ww=site.$window.width(),a.$sidebar.length&&a.sidebar.processLock()}}),a.$scrollTo.on("click",function(b){return b.preventDefault(),a.scrollToAnchor(this.hash,!0,!0),!1})},checkHash:function(){var a=this,b=window.location.hash;b&&("#welcome"===b?site.modal.alert('<span>Welcome to your MixItUp account!</span> <span>Thanks for signing up. You may now post to the <a href="'+window.location.origin+'/support">support forums »</a></span>'):a.scrollToAnchor(window.location.hash))},scrollToAnchor:function(b,c,d){var e=this,f=a(b);if(f.length){var g=e.header.height,h=f.offset().top,i=h-g-1;if(d&&window.history.replaceState({},"",b),c)e.$html.add(e.$body).animate({scrollTop:i},"fast");else{var j=window.scrollX||document.documentElement.scrollLeft;window.scrollTo(j,i)}}else{{var k=b.replace("#",".");a('[data-filter="'+k+'"]')}k.indexOf("prop")>-1||k.indexOf("group")>-1?site.configObject.load=k:k.indexOf("method")>-1&&(site.apiMethods.load=k)}},backToTop:{init:function(){var b=this;b.$backToTop=a("#BackToTop"),b.bindHandlers(),b.hideShow()},bindHandlers:function(){var a=this;a.$backToTop.on("click",function(){a.toTop()})},hideShow:function(){var a=this;site.scrollTop>site.$document.height()-site.wh-140?a.$backToTop.addClass("hidden"):site.scrollTop>1e3?a.$backToTop.removeClass("hidden"):a.$backToTop.addClass("hidden")},toTop:function(){site.$html.add(site.$body).animate({scrollTop:0},"fast")}},header:{openDelay:null,lightHamburger:!1,init:function(){var b=this;b.height=site.$header.height(),b.$logo=site.$header.find(".logo"),b.$hamburger=a("#Hamburger"),b.$globalNav=a("#GlobalNav"),b.$links=b.$globalNav.find("li"),b.$tracer=a("#Tracer"),b.$logoGrid=a("#LogoGrid"),b.$toggleOptions=a("#ToggleOptions"),b.$hoverZone=b.$hamburger.add(b.$globalNav),b.$activeLink=b.$links.filter(".current-menu-item"),b.$activeLink.length&&b.trace(b.$activeLink),b.bindHandlers(),b.mixLogo()},bindHandlers:function(){var b=this,c=!1,d=null,e=null;site.platform.touch?b.$hamburger.on("click",function(){site.$body.toggleClass("nav-open")}):(b.$hoverZone.on({"mouseover.hover":function(){c?clearTimeout(e):d=setTimeout(function(){c=!0,b.showNav()},200)}}),b.$hamburger.on({"mouseover.hover":function(){c&&(e=setTimeout(function(){c=!1,b.hideNav()},200))},"mouseout.hover":function(){c||clearTimeout(d)}}),b.$globalNav.on({"mouseout.hover":function(){c?e=setTimeout(function(){c=!1,b.hideNav()},200):clearTimeout(d)}})),b.$globalNav.find("li a").on("click",function(a){a.preventDefault();var c=this.href;return b.hideNav(),b.$hoverZone.off(".hover"),setTimeout(function(){window.location=c},200),!1}),b.$toggleOptions.on("click",function(){site.$body.hasClass("sandbox-config-open")?site.$body.removeClass("sandbox-config-open"):site.$body.addClass("sandbox-config-open")}),b.$tracer.length&&b.$links.on({mouseover:function(){b.trace(a(this))}})},showNav:function(){site.$body.addClass("nav-open"),self.openDelay=setTimeout(function(){site.$body[0].style.overflowX="hidden"},200)},hideNav:function(){clearTimeout(self.openDelay),site.$body[0].style.overflowX="";var a=function(){site.$body.removeClass("nav-open")};requestAnimationFrame(a)},trace:function(a){var b=this,c=a.position().top;b.$tracer.animate({top:c+"px"},50)},style:function(){var a=this;!(site.scrollTop>=50)||site.$sidebar.length&&"static"!==site.sidebar.activePositioning||a.lightHamburger?site.scrollTop<=50&&a.lightHamburger&&(a.$hamburger.removeClass("light"),a.lightHamburger=!1):(a.$hamburger.addClass("light"),a.lightHamburger=!0)},mixLogo:function(){var a=this;a.$logoGrid.mixItUp({filterOnLoad:"all",controls:{enable:!1},animation:{effects:"fade stagger(50ms) translateY(-50px) translateZ(-1000px)",easing:"cubic-bezier(0.175, 0.885, 0.32, 1.275)"},callbacks:{onMixLoad:function(){setInterval(function(){a.$logoGrid.mixItUp("sort","random",function(){a.$logoGrid.mixItUp("sort","random",function(){a.$logoGrid.mixItUp("sort","default")})})},6e3)}}})}},sidebar:{activePositioning:"fixed",activeOffset:0,lockOn:[],lockOff:[],offset:[],init:function(){var a=this;a.$sidebar=site.$sidebar,a.$container=a.$sidebar.children("div.container"),a.$locked=a.$sidebar.find("article.locked"),a.activePositioning=a.$sidebar.hasClass("static")?"static":"fixed",a.processLock()},processLock:function(){var b=this,c=window.scrollY||document.documentElement.scrollTop,d=window.scrollX||document.documentElement.scrollLeft;if(window.scrollTo(0,0),b.unsetSidebar(),b.$locked.length)for(var e=0;e<b.$locked.length;e++){var f=b.$locked.eq(e),g=a(f.attr("data-lock-to")),h=f.offset().top,i=g.offset().top,j=b.offset[e-1]||0;b.lockOn[e]=i-h+j,b.lockOff[e]=i-site.header.height-1,b.offset[e]=h-site.header.height-1}window.scrollTo(d,c),b.setSidebar()},lockArticles:function(){for(var a=this,b=0;b<a.lockOn.length;b++)if(site.scrollTop<a.lockOn[b])a.activeOffset=0,a.activePositioning="fixed",a.setSidebar();else if(site.scrollTop>=a.lockOn[b]){if(site.scrollTop<a.lockOff[b]){var c=a.offset[b-1]||0;a.activeOffset=a.lockOn[b]-c,a.activePositioning="static",a.setSidebar();break}if(site.scrollTop<a.lockOn[b+1]||b===a.lockOn.length-1){a.activeOffset=-1*a.offset[b],a.activePositioning="fixed",a.setSidebar();break}}},unsetSidebar:function(){var a=this;a.$sidebar.removeClass("static").removeAttr("style")},setSidebar:function(){var a=this;"fixed"===a.activePositioning?a.$sidebar.removeClass("static"):a.$sidebar.addClass("static"),a.$sidebar.css("margin-top",a.activeOffset+"px")}},sandBox:{mode:"",changingLayout:!1,init:function(){var b=this;b.$sandBox=site.$sandBox,b.$toggleConfig=a("#ToggleConfig"),b.$toggleLayout=a("#ToggleLayout"),b.configOpen=!1,b.easingString="ease",b.effectsArray=["fade","translateZ(-360px)","stagger(34ms)"],b.effectsString="fade translateZ(-360px) stagger(34ms)",b.duration=400,b.$liveConfig=a("#LiveConfig"),b.$export=a("#Export"),b.$toggles=b.$liveConfig.find("input"),b.$sliders=b.$liveConfig.find(".slider"),b.$sandBox.mixItUp({animation:{duration:b.duration,effects:b.effectsString,easing:b.easingString,queueLimit:3,animateChangeLayout:!0,animateResizeTargets:!1},debug:{enable:!0},load:{sort:"random"}}),b.bindHandlers()},bindHandlers:function(){var b=this;b.$toggleConfig.on("click",function(){var c=a(this);b.$sandBox.hasClass("config-open")?b.$sandBox.add(c).removeClass("config-open"):(b.$sandBox.add(c).addClass("config-open"),site.analytics.pushEvent("SandBox","Open Config"))}),b.$sandBox.on("mixStart",function(a,c,d,e){e._changingLayout&&b.$sandBox.mixItUp("setOptions",{animation:{animateResizeTargets:!0}})}),b.$toggleLayout.on("click",function(){""===b.mode?(b.mode="list",b.changeLayout("list"),b.$toggleLayout.addClass("grid-icon")):(b.mode="",b.changeLayout(""),b.$toggleLayout.removeClass("grid-icon"))}),b.$toggles.on("change",function(){var c=a(this).parent(),d=c.find(".slider"),e=d.find(".scrubber"),f=c.attr("data-effect"),g=e.attr("data-value");c.hasClass("active")?(c.removeClass("active"),b.removeEffect(f)):(c.addClass("active"),b.addEffect("undefined"!=typeof g?f+"("+g+")":f)),b.liveConfig()}),b.$export.on("click",function(){var a="{\n	animation: {\n		duration: "+b.duration+",\n		effects: '"+b.effectsString+"',\n		easing: '"+b.easingString+"'\n	}\n}";site.modal.$content.children().hide(),site.modal.$content.find("textarea").show().html(a),site.modal.show(),site.analytics.pushEvent("SandBox","Export Config",encodeURIComponent(a))}),b.$sliders.each(function(){site.slider.init(a(this))})},changeLayout:function(a){var b=this;b.$sandBox.mixItUp("changeLayout",{containerClass:a},function(){b.$sandBox.mixItUp("setOptions",{animation:{animateResizeTargets:!1}})})},addEffect:function(a){var b=this;b.effectsArray.push(a)},removeEffect:function(a){for(var b=this,c=0;c<b.effectsArray.length;c++){var d=b.effectsArray[c];if(d.indexOf(a)>-1){b.effectsArray.splice(c,1);break}}},liveConfig:function(){var a=this;a.effectsString=a.effectsArray.join(" "),a.$sandBox.mixItUp("setOptions",{animation:{effects:a.effectsString}})},liveEasing:function(a){var b=this;b.easingString=a,b.$sandBox.mixItUp("setOptions",{animation:{easing:a}})},liveDur:function(a){var b=this;b.duration=a,b.$sandBox.mixItUp("setOptions",{animation:{duration:a}})}},sandBoxPagination:{init:function(){var a=this;a.$pagination=site.$sandBoxPagination,a.$pagination.mixItUp({pagination:{generatePagers:!0,limit:4,pagerClass:"btn"},load:{sort:"value:asc"}})}},demo1:{init:function(){var a=this;a.$demo1=site.$demo1,a.$demo1.mixItUp({pagination:{limit:3},controls:{enable:!1},animation:{duration:800,effects:"fade rotateX(10deg) translateZ(-250px)"},layout:{display:"block"},callbacks:{onMixLoad:function(){setInterval(function(){a.$demo1.mixItUp("sort","random")},2e3)}}})}},configObject:{load:!1,init:function(){var b=this,c={enable:!0,effects:"fade scale(0.8)",duration:400,queue:!0,queueLimit:1,animateResizeTargets:!0};b.$configObjectGrid=site.$configObjectGrid,b.$configObjectMenu=a("#ConfigObjectMenu"),b.$propFilters=b.$configObjectMenu.find(".prop"),b.$groups=b.$configObjectMenu.find(".group"),b.$groupNames=b.$groups.find(".label"),b.$configObjectGrid.mixItUp({animation:{enable:!1},load:{filter:b.load||".learn-more"},callbacks:{onMixLoad:function(){site.sidebar.processLock();var d=b.load?a('[data-filter="'+b.load+'"]'):a();d.closest(".group").length&&(b.$configObjectMenu.find(".group.open").removeClass("open").find(".active").removeClass("active"),d.closest(".group").addClass("open").find(".label").addClass("active")),b.$configObjectGrid.mixItUp("setOptions",{animation:c,callbacks:{onMixEnd:function(a){var b=a.activeFilter,c="none"===b?"":b.replace(".","#");window.history.replaceState({},"",c),site.sidebar.processLock(),site.scrollToAnchor("#ConfigObjectGrid",!0,!1)}}})},onMixEnd:function(a){if(b.load){setTimeout(function(){site.scrollToAnchor("#configuration-object",!1,!1)},100)}site.analytics.pushEvent("Docs","View Items",a.activeFilter)}}}),b.bindHandlers()},bindHandlers:function(){var b=this;b.$groupNames.on("click",function(){var c=a(this),d=c.closest(".group"),e=c.attr("data-filter"),f=function(){var a=b.$configObjectMenu.outerHeight(!0);b.$configObjectGrid[0].style.minHeight=a+"px"};if(d.hasClass("open")){c.removeClass("active"),b.$propFilters.removeClass("active"),d.removeClass("open");{setTimeout(function(){f(),b.$configObjectGrid.mixItUp("filter",".learn-more")},10)}}else{b.$groups.removeClass("open"),b.$groupNames.removeClass("active"),b.$propFilters.removeClass("active"),c.addClass("active"),d.addClass("open");{setTimeout(function(){f(),b.$configObjectGrid.mixItUp("filter",e)},10)}}})}},topicsGrid:{init:function(){self.$topicsGrid=site.$topicsGrid,self.$topicsGrid.mixItUp({animation:{effects:"fade translateZ(-20px)",duration:500},selectors:{target:".item"},layout:{display:"block"},pagination:{limit:10,pagerClass:"btn"}})}},apiMethods:{load:!1,init:function(){var b=this,c={enable:!0,effects:"fade scale(0.8)",duration:400,queue:!0,queueLimit:1,animateResizeTargets:!0};b.$apiMethodsGrid=site.$apiMethodsGrid,b.$apiMethodsMenu=a("#APIMethodsMenu"),b.$apiMethodsGrid.mixItUp({animation:{enable:!1},selectors:{filter:".method-filter"},load:{filter:b.load||".learn-more"},callbacks:{onMixLoad:function(){site.sidebar.processLock(),b.$apiMethodsGrid.mixItUp("setOptions",{animation:c,callbacks:{onMixEnd:function(a){var b=a.activeFilter,c="none"===b?"":b.replace(".","#");window.history.replaceState({},"",c),site.sidebar.processLock(),site.scrollToAnchor("#APIMethodsGrid",!0,!1)}}})},onMixEnd:function(a){if(b.load){setTimeout(function(){site.scrollToAnchor("#api-methods",!1,!1)},100)}site.analytics.pushEvent("Docs","View Item",a.activeFilter)}}})}},bbpForms:{forms:[],init:function(b){a("#editor-buttons-css").remove();var c=this,d={form:b[0],errors:[],strict:!1,$form:b,$inputs:b.find("input, select"),$validate:b.find(".validate input, .validate select, .validate textarea"),$submit:b.find('button[type="submit"]'),$errors:b.find(".alert")};c.forms.push(d),c.bindHandlers(d)},bindHandlers:function(a){var b=this;a.$form.on("submit",function(c){return c.preventDefault(),site.mixForms.validate(a)?b.post(a):a.strict||site.mixForms.enableStrict(a),!1})},post:function(a){a.form.submit()}},mixForms:{forms:[],stripeResponse:!1,init:function(b){var c=this,d={form:b[0],errors:[],strict:!1,posting:!1,method:a("#Method").val(),$form:b,$infos:b.find(".info"),$inputs:b.find("input, select"),$validate:b.find(".validate input, .validate select"),$submit:b.find('button[type="submit"]'),$errors:b.find(".alert"),$confirmation:a("section.confirmation")};c.forms.push(d),c.bindHandlers(d)},bindHandlers:function(b){var c=this;b.$infos.on("click",".close",function(){a(this).parent().fadeOut()}),b.$form.on("submit",function(a){return a.preventDefault(),c.validate(b)&&!b.posting?"mix_checkout"===b.method?c.getStripeData(b):c.post(b):b.strict||c.enableStrict(b),!1})},enableStrict:function(a){var b=this;a.strict=!0,a.$validate.on("keyup",function(){b.validate(a)}),a.$validate.filter('[type="checkbox"], select').on("change",function(){b.validate(a)})},validate:function(a){var b=this,c=function(a){var b=/\S+@\S+\.\S+/;return b.test(a)},d=function(a){if(!a)return!1;if(/[^0-9-\s]+/.test(a))return!1;var b=0,c=0,d=!1;a=a.replace(/\D/g,"");for(var e=a.length-1;e>=0;e--){var f=a.charAt(e),c=parseInt(f,10);d&&(c*=2)>9&&(c-=9),b+=c,d=!d}return b%10===0};a.errors=[];for(var e=0;e<a.$validate.length;e++){var f=a.$validate.eq(e),g=f[0],h=f.closest(".field"),i=h.attr("data-msg"),j=g.value,k=function(b,c){b?h.removeClass("invalid"):(a.errors.indexOf(c)<0&&a.errors.push(c),h.addClass("invalid"))};if(h.hasClass("required-text")){var l=h.hasClass("products")?"You don&apos;t have any products in your cart.":"Please fill out all required fields.";k(j.length,l)}if(h.hasClass("email")&&k(c(j),"Please enter a valid email address."),h.hasClass("password")&&k(j.length>=7,"Passwords must be at least 7 characters long."),h.hasClass("new-password")&&k(j.length>=7||!j.length,"Passwords must be at least 7 characters long."),h.hasClass("confirm-password")){var m=a.$validate.filter('[name="user_password"]').val();k(j===m,"Passwords do not match.")}h.hasClass("cc-no")&&k(d(j),"Please enter a valid credit card number."),h.hasClass("required-check")&&k(f.is(":checked"),i),h.hasClass("required-dropdown")&&k(j.length,"Please fill out all required fields.")}return a.errors.length?(b.showErrors(a),!1):(b.hideErrors(a),!0)},showErrors:function(a){for(var b="",c=0;c<a.errors.length;c++){var d=a.errors[c];b+="<span>"+d+"</span>"}a.$errors.html(b).finish().fadeIn()},hideErrors:function(b){b.$errors.fadeOut(function(){b.errors.length||a(this).empty()})},getStripeData:function(a){var b=this,c=a.$form.find('input[name="cc_no"]').val(),d=a.$form.find('select[name="cc_yr"]').val(),e=a.$form.find('select[name="cc_mo"]').val(),f=a.$form.find('input[name="cc_cvc"]').val();site.loader.show(),Stripe.card.createToken({number:c,cvc:f,exp_month:e,exp_year:d},function(c,d){b.stripeResponse=d,b.stripeResponse.error?(a.errors.push(b.stripeResponse.error.message),b.handleServerErrors(a),site.analytics.pushEvent("Transaction","Stripe Error",b.stripeResponse.error.message)):b.post(a)})},post:function(b){var c=this,d={action:"mixitup_members",nonce:mixitupAjax.ajaxNonce};b.posting=!0;for(var e=0;e<b.$inputs.length;e++){var f=b.$inputs[e];d[f.name]=f.value}c.stripeResponse&&(a.extend(d,{fingerprint:c.stripeResponse.card.fingerprint,token:c.stripeResponse.id,type:c.stripeResponse.card.type}),delete d.cc_no,delete d.cc_yr,delete d.cc_mo,delete d.cc_cvc),b.$submit.attr("disabled",!0),b.$form.addClass("disabled"),site.loader.loop||site.loader.show(),a.post(mixitupAjax.ajaxURL,d,function(a){a.success?c.handleServerSuccess(b,a):c.handleServerErrors(b,a),b.posting=!1},"json")},handleServerSuccess:function(b,c){site.loader.hide(),"login_successful"===c.success.type?"self"===c.redirect?window.location.reload():window.location.href=window.location.protocol+"//"+location.host:"registration_successful"===c.success.type?window.location.href=window.location.protocol+"//"+location.host+"#welcome":"checkout_successful"===c.success.type?(site.cartManager.contents=[],site.cartManager.setCart(),b.$form[0].reset(),a("section").hide(),a("#TransactionID").text(c.success.transaction_id),b.$confirmation.fadeIn(),site.analytics.pushEvent("Transaction","Success",c.success.transaction_id)):"account_update_successful"===c.success.type?(b.$validate.filter("[type=password]").each(function(){this.value=""}),site.modal.alert("<span>Your account was updated successfully.</span>")):"help_email_sent"===c.success.type?site.modal.alert("<span>You have been sent an email with instructions about to reset your password.</span>"):"reset_email_sent"===c.success.type?site.modal.alert("<span>Your password has been reset succesfully.</span> <span>You have been sent an email containing your new password.</span>"):"sign_up_successful"===c.success.type&&(site.modal.$content.find("form").hide(),site.modal.$content.find(".thankyou").fadeIn())},handleServerErrors:function(a,b){for(var c=this,d=0;b&&d<b.errors.length;d++){var e=b.errors[d],f=e.name,g=e.message;a.$inputs.filter('[name="'+f+'"]').parent().addClass("invalid"),a.errors.indexOf(g)<0&&a.errors.push(g),"gravity_forms_error"===e.type?site.analytics.pushEvent("Transaction","Server Error",""):"payment_error"===e.type&&site.analytics.pushEvent("Transaction","Stripe Error (Server-side)","")}c.showErrors(a),site.loader.hide(),a.$form.removeClass("disabled"),a.$submit.removeAttr("disabled"),c.enableStrict(a)}},addToCart:{adding:!1,init:function(){var a=this;a.$addToCart=site.$addToCart,a.bindHandlers()},bindHandlers:function(){var a=this;a.$addToCart.on("click",function(){a.adding||a.add(this)})},add:function(b){var c=this,d=a(b),e=d.attr("data-product"),f={action:"mixitup_members",nonce:mixitupAjax.ajaxNonce,method:"mix_get_product_data",slug:e};c.adding=!0,site.loader.show(),a.post(mixitupAjax.ajaxURL,f,function(b){if(b.success){for(var e=a.parseJSON(b.success.product_data),f=!1,g=0;g<site.cartManager.contents.length&&!f;g++){var h=site.cartManager.contents[g];h.slug===e.slug&&(f=!0,site.loader.hide(),site.modal.alert("<span>Product already in cart.</span>",!0)),h.slug.indexOf("commercial")>-1&&e.slug.indexOf("commercial")>-1&&(f=!0,site.loader.hide(),site.modal.alert("<span>Please choose either a single-developer or multi-developer license.</span>"))}f||(site.loader.hide(),site.cartManager.contents.push(e),site.cartManager.setCart(),d.attr("disabled",!0).text("In Cart").siblings().fadeIn(),site.analytics.pushEvent("Transaction","Add To Cart",e.slug))}else site.modal.alert("<span>Product not found.</span>",!0),site.analytics.pushEvent("Transaction","Product not found",JSON.stringify(b));c.adding=!1},"json")}},cartManager:{contents:[],example:[{slug:"commercial_single",name:"Single-developer Commercial License",price:"19",type:"license",gfId:5},{slug:"commercial_multi",name:"Multi-developer Commercial License",price:"49",type:"license",gfId:6}],init:function(){var a=this;a.$cartLink=site.$cartLink,a.getCart()},getCart:function(){var b=this,c=(document.cookie,function(a){return a.replace(/^\s+|\s+$/gm,"")}),d=function(a){for(var b=a+"=",d=document.cookie.split(";"),e=0;e<d.length;e++){var f=c(d[e]);if(0===f.indexOf(b))return f.substring(b.length,f.length)}return""},e=d("mix_cart");b.contents=e?a.parseJSON(e):[],b.render()},setCart:function(){var a=this,b=function(a){var b=new Date;return b.setTime(b.getTime()+24*a*60*60*1e3),"expires="+b.toGMTString()},c=b(14);document.cookie="mix_cart="+JSON.stringify(a.contents)+";path=/;"+c,a.render()},render:function(){var a=this,b=a.contents.length?a.contents.length:0;a.$cartLink.find("span").text(b),b?a.$cartLink.addClass("show"):a.$cartLink.removeClass("show")}},cart:{filterString:":not(.info, .remove)",total:0,products:"",init:function(){var b=this;b.$cart=site.$cart,b.$items=b.$cart.find,b.$total=a("#Total"),b.$productsField=a("#Products"),site.loader.show(),b.$cart.mixItUp({animation:{effects:"fade",duration:400},selectors:{target:"div"},layout:{display:"block"},load:{filter:b.filterString},callbacks:{onMixEnd:function(a){b.showCta(a),b.updateTotal(a),site.loader.hide()}}}),b.bindHandlers()},bindHandlers:function(){var b=this;b.$cart.on("click",".close",function(){for(var c=a(this).closest(".item").addClass("remove"),d=c.attr("data-name").split("|"),e=d[0],f=0;f<site.cartManager.contents.length;f++){var g=site.cartManager.contents[f];g.slug===e&&site.cartManager.contents.splice(f,1),site.cartManager.setCart()}b.$cart.mixItUp("filter",b.filterString),site.analytics.pushEvent("Transaction","Remove from Cart",e)})},showCta:function(a){var b=this,c=a.$show.filter(".license"),d=a.$show.filter(".extension"),e=a.$show.filter(".cta-licenses"),f=a.$show.filter(".cta-extensions");!c.length&&!e.length&&(b.filterString+=", .cta-licenses"),!d.length&&!f.length&&(b.filterString+=", .cta-extensions"),(!c.length&&!e.length||!d.length&&!f.length)&&b.$cart.mixItUp("filter",b.filterString)},updateTotal:function(b){var c=this;c.total=0,c.products="",$items=b.$show.filter(".item");for(var d,e=0;d=$items[e];e++){var f=a(d),g=1*f.attr("data-price"),h=f.attr("data-name"),i=e===$items.length-1?"":", ";c.total+=g,c.products+=h+i}c.$total.text("$"+c.total+".00 USD"),c.$productsField.length&&(c.$productsField[0].value=c.products)}},tutsGrid:{init:function(){var b=this;b.$search=a("#SearchTuts"),b.$targets=site.$tutsGrid.find(".item"),b.$form=site.$tutsGrid.find("form"),b.sort="order:desc",site.$tutsGrid.mixItUp({pagination:{limit:8,generatePagers:!0,pagerClass:"btn"},load:{sort:b.sort},callbacks:{onMixEnd:function(a){b.sort=a.activeSort}}}),b.bindHandlers()},bindHandlers:function(){var b=this,c=null;b.$search.on({keydown:function(){clearTimeout(c)},keyup:function(){var d=a(this).val().toLowerCase();clearTimeout(c),c=setTimeout(function(){b.search(d)},500)}}),b.$form.on("submit",function(a){a.preventDefault()})},search:function(b){var c=this,d=a();if(b.length>2){for(var e=0;e<c.$targets.length;e++){var f=c.$targets[e].children[0].innerHTML.toLowerCase(),g=c.$targets[e].children[1].innerHTML.toLowerCase();(f.indexOf(b)>-1||g.indexOf(b)>-1)&&(d=d.add(c.$targets[e]))}site.$tutsGrid.mixItUp("filter",d)}else site.$tutsGrid.mixItUp("filter","all")}},slider:{init:function(a){var b=a.find(".scrubber"),c=parseFloat(b.data("value")),d=parseFloat(a.data("min")),e=parseFloat(a.data("max")),f=a.data("unit"),g=e-d,h=(c-d)/g*100,i=a.closest(".effect"),j=i.data("effect"),k=a.width(),l=null,m=null,n=null,o=null,p=null,q=function(a){var b=site.platform.touch?a.originalEvent.touches[0]||a.originalEvent.changedTouches[0]||a:a;return b};b.css("left",h+"%"),b.on("mousedown touchstart",function(a){i.hasClass("active")&&(a.preventDefault(),a=q(a),b.addClass("down"),l=a.pageX,site.$window.on({"mousemove.scrub touchmove.scrub":function(a){a=q(a),m=a.pageX,n=m-l,o=n/k*100,p=1*(h+o).toFixed(),p=0>p?0:p>100?100:p,b.css("left",p+"%"),c=g*(p/100)+d,c=(1e3*c).toFixed()/1e3,c="scale"===j?parseFloat(Math.round(100*c)/100).toFixed(2):1*c.toFixed(),b.attr("data-value",c+f)},"mouseup.scrub touchend.scrub":function(){site.$window.off(".scrub"),b.removeClass("down"),h=p,"duration"!==j?(site.sandBox.removeEffect(j),site.sandBox.addEffect(j+"("+c+f+")"),site.sandBox.liveConfig()):site.sandBox.liveDur(c)}}))})}},downloadLinks:{loading:!1,init:function(){var a=this;a.$downloadLinks=site.$downloadLinks,a.bindHandlers()},bindHandlers:function(){var b=this;b.$downloadLinks.on("click",function(){var c=a(this),d=c.attr("data-repo");b.loading||b.getFile(d)})},getFile:function(b){var c=this,d={action:"mixitup_members",nonce:mixitupAjax.ajaxNonce,method:"mix_get_extension",repo_name:b};c.loading=!0,site.loader.show(),a.post(mixitupAjax.ajaxURL,d,function(a){a.success?(c.loading=!1,site.loader.hide(),site.analytics.pushEvent("Download","ZipBall",a.success.file_path),window.location=a.success.file_path):(site.loader.hide(),site.modal.alert("<span>File not found.</span>"))},"json")}},manageSubscribe:{action:null,topicId:null,posting:!1,init:function(){var a=this;a.$button=site.$subscribeLink,a.bindHandlers()},bindHandlers:function(){var a=this;a.$button.on("click",function(){a.posting||(a.posting=!0,site.loader.show(),a.action=a.$button.attr("data-action"),a.topicId=a.$button.attr("data-topic-id"),a.post())})},post:function(){var b=this,c={action:"mixitup_members",method:"mix_subscriptions",nonce:mixitupAjax.ajaxNonce,subscribe_action:b.action,topic_id:b.topicId};a.post(mixitupAjax.ajaxURL,c,function(a){a.success&&(b.posting=!1,site.loader.hide(),window.location.reload())},"json")}},shareLinks:{init:function(){var a=this;a.$links=site.$shareLinks.children(),a.bindHandlers()},bindHandlers:function(){var a=this;a.$links.on("click",function(a){a.preventDefault();var b=this.href;return window.open(b,"","menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600"),site.analytics.pushEvent("Social Media","Share",b),!1})}},downloadIntercepts:{init:function(){var a=this;a.$links=site.$downloadIntercepts,a.bindHandlers()},bindHandlers:function(){var a=this;a.$links.on("click",function(a){a.preventDefault(),site.modal.interceptDownload();var b=this.href,c="https://github.com/patrickkunka/mixitup/archive/master.zip";return delay=setTimeout(function(){window.location=c},3e3),site.analytics.pushEvent("Download","ZipBall",b),!1})}},modal:{init:function(){var b=this;b.$modalWrapper=a("#ModalWrapper"),b.$modal=a("#Modal"),b.$close=a("#Close"),b.$content=a("#ModalContent"),b.$modalWrapper.mixItUp({controls:{enable:!1},selectors:{target:".modal"},layout:{display:"block"},load:{filter:"none"},animation:{duration:300,effects:"fade translateZ(-50px) translateY(-16%)",easing:"cubic-bezier(0.175, 0.885, 0.32, 1.275)",queue:!0}}),b.bindHandlers()},alert:function(a,b){var c=this,d=b?"info alert":"info";c.$content.children().hide(),c.$content.find(".message").show().html('<div class="'+d+'">'+a+"</div>"),c.show()},interceptDownload:function(){var a=this;a.$content.children().hide(),a.$content.find(".intercept").show(),a.show()},bindHandlers:function(){var a=this;a.$close.on("click",function(){a.hide()})},show:function(){var a=this;a.$modalWrapper.show().mixItUp("filter","all")},hide:function(){var a=this;a.$modalWrapper.mixItUp("filter","none",function(){a.$modalWrapper.hide()})}},loader:{loop:!1,init:function(){var b=this;b.$loaderWrapper=a("#LoaderWrapper"),b.$loaderWrapper.mixItUp({controls:{enable:!1},selectors:{target:"i"},load:{filter:"none"},animation:{queue:!0,queueLength:10,duration:"200",effects:"fade scale stagger",easing:"cubic-bezier(0.6, -0.28, 0.735, 0.045)"}})},show:function(){var a=this,b=function(){a.$loaderWrapper.mixItUp("filter","none",function(){a.loop?c():d()})},c=function(){a.$loaderWrapper.mixItUp("filter","all",function(){b()})},d=function(){a.$loaderWrapper.fadeOut(200)};a.loop=!0,a.$loaderWrapper.fadeIn(200,function(){a.$loaderWrapper.mixItUp("filter","all",function(){b()})})},hide:function(){var a=this;a.loop=!1}},analytics:{init:function(){var b=this;b.$outBound=a("a.outbound"),b.bindHandlers()},bindHandlers:function(){var b=this;b.$outBound.on("click",function(c){c.preventDefault();var d=this.href,e=a(this);return e.hasClass("zip")?b.pushEvent("Download","ZipBall",d):b.pushEvent("External Link","GitHub",d),setTimeout(function(){window.location=d},100),!1})},pushEvent:function(a,b,c){"undefined"!=typeof _gaq&&_gaq.push(["_trackEvent",a,b,c])}}}),a.fn.serializeObject=function(){var b={},c=this.serializeArray();return a.each(c,function(){void 0!==b[this.name]?(b[this.name].push||(b[this.name]=[b[this.name]]),b[this.name].push(this.value||"")):b[this.name]=this.value||""}),b},a(function(){site.init()})}(jQuery);
//# sourceMappingURL=map/main.map