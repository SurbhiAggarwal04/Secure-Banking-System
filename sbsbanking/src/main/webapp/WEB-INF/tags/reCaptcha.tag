<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ tag import='net.tanesha.recaptcha.ReCaptcha' %>
<%@ tag import='net.tanesha.recaptcha.ReCaptchaFactory' %>
<%@ tag import="net.tanesha.recaptcha.ReCaptchaImpl" %>
<%@ attribute name='privateKey' required='true' rtexprvalue='true' %>
<%@ attribute name='publicKey' required='true' rtexprvalue='true' %>

<%
    //ReCaptcha c = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
    ReCaptcha c = ReCaptchaFactory.newSecureReCaptcha(publicKey, privateKey, false);
    ((ReCaptchaImpl) c).setRecaptchaServer("https://www.google.com/recaptcha/api");
    out.print(c.createRecaptchaHtml(null, null));
%>
