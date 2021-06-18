<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Login</title>
    </head>
    <body>

        <form method="post" action="validaLogin.do">
            <div align="center">
                <body bgcolor="#CEF6EC">
                    <td><font size=5>COVALE AGRÍCOLA</font></td>

                    <table>
                        <tr>
                            <td>Usuário</td>
                            <td><input type="text" name="username" /></td>
                        </tr>
                        <tr>
                            <td>Senha</td>
                            <td><input type="password" name="password" /></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="Login" /></td>
                        </tr>
                    </table>

                </body>
            </div>
        </form>

    </body>
</html>