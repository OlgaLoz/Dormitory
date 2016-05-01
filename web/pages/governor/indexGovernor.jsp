<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Заведующая</title>

        <link href="../../Styles/bootstrap.min.css" rel="stylesheet">
        <link href="../../Styles/template.css" rel="stylesheet">

        <link rel="shortcut icon" href="../../img/icon.ico" type="image/x-icon">
        <!--[if lt IE 9]>
        <script type="text/javascript" src="/Scripts/html5shiv.js"></script>
        <script type="text/javascript" src="/Scripts/respond.min.js"></script>
        <![endif]-->

    </head>
    <body>
        <div id="wrapper">
            <!-- Sidebar -->
            <div id="sidebar-wrapper">
                <ul class="sidebar-nav">
                    <li class="sidebar-brand">
                        <span class="dropdown">
                            <span class="glyphicon glyphicon-user" data-toggle="dropdown"></span>
                            Заведующий
                            <ul class="dropdown-menu ">
                                <li><form action="/Action/Logout" method="post">
                                    <input type="submit" value="Выход" class="btn btn-link btn-block btn-sm">
                                </form></li>
                            </ul>
                        </span>
                    </li>
                    <li>
                        <form action="/Action/GetAllSettledStudents" method="post">
                            <a href="#">
                                <input type="submit" value="Прошли доктора" class="btn btn-link gray-button ">
                                <input type="hidden" name="status" value="BodyCheckPassed"/>
                            </a>
                        </form>
                        </li>
                    <li>
                        <form action="/Action/Download" method="post">
                            <a href="#">
                                <input type="submit" value="Прошли доктора(xlsx)" class="btn btn-link gray-button ">
                                <input type="hidden" value="BodyCheckPassed" name="student_status">
                                <input type="hidden" value="xlsx" name="doc_type">
                            </a>
                        </form>
                        <form action="/Action/GetAllSettledStudents" method="post">
                            <a href="#">
                                <input type="submit" value="Заселены" class="btn btn-link gray-button ">
                                <input type="hidden" name="status" value="Settled"/>
                            </a>
                        </form>
                        <form action="/Action/Download" method="post">
                            <a href="#">
                                <input type="submit" value="Заселенные(xlsx)" class="btn btn-link gray-button ">
                                <input type="hidden" value="Settled" name="student_status">
                                <input type="hidden" value="xlsx" name="doc_type">
                            </a>
                        </form>
                    </li>
                    <li>
                        <hr>
                        <form action="/Action/GovernorDownload" method="post">
                            <a href="#">
                                <input type="submit" value="Прошли доктора(документы)" class="btn btn-link gray-button ">
                                <input type="hidden" name="status" value="BodyCheckPassed"/>
                            </a>
                        </form>
                    </li>
                    <li>
                    <li>
                        <form action="/Action/GovernorDownload" method="post">
                            <a href="#">
                                <input type="submit" value="Заселены(документы)" class="btn btn-link gray-button ">
                                <input type="hidden" name="status" value="Settled"/>
                            </a>
                        </form>
                    </li>
                    <li>
                </ul>
            </div>
            <!-- /#sidebar-wrapper -->

            <!-- Page Content -->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <a href="#menu-toggle" class="btn btn-info" id="menu-toggle">
                                <span class="glyphicon glyphicon-plus"></span>
                            </a>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <form action="/Action/SearchByLastNameToGovernor" method="post">
                            <div class="input-group">
                                 <span class="input-group-btn">
                                      <input type="submit" value="Найти" class="btn btn-info" type="button">
                                 </span>
                                <input type="text" name="lastNameInput" class="form-control" placeholder="Введите фамилию...">
                            </div><!-- /input-group -->
                        </form>
                    </div>
                </div>
                <br>
                <form action="/Action/CheckStudentsByGovernor" method="post">
                    <c:if test = "${students != null && students.size() != 0}">

                        <div class="table-responsive">
                            <table class="table table-hover" >
                                <tr>
                                    <td><b>Имя</b></td>
                                    <td><b>Отчество</b></td>
                                    <td><b>Фамилия</b></td>
                                    <td><b>Номер группы</b></td>
                                    <c:if test = "${isSettled == 'bodyCheckPassed'}">
                                        <td> <span class="glyphicon glyphicon-ok" style="color: green"></span></td>
                                        <td> <span class="glyphicon glyphicon-remove" style="color: red"></span></td>
                                    </c:if>
                                </tr>
                                <c:forEach  var="data" items="${students}" >
                                    <tr>
                                        <td>${data.getFirstName()}</td>
                                        <td>${data.getMidName()}</td>
                                        <td>${data.getLastName()}</td>
                                        <td>${data.getGroupNumber()}</td>

                                        <c:if test = "${isSettled == 'bodyCheckPassed'}">
                                            <td>
                                                <div class="checkbox">
                                                    <label><input type="checkbox" name ="checkers" value="${data.getStudentId()}"></label>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="checkbox">
                                                    <label><input type="checkbox" name ="uncheckers" value="${data.getStudentId()}"></label>
                                                </div>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <div class="col-lg-2 col-lg-offset-5">
                            <input type="submit" value="Сохранить" class="btn btn-success btn-block btn-lg">
                        </div>
                    </c:if>
                    <c:if test = "${students == null || students.size() == 0}">
                        <div class="col-lg-2 col-lg-offset-5">
                            <label class="label-success label">Студентов нет :)</label>
                        </div>
                    </c:if>
                </form>

                <div class="col-xs-12 footer">
                    <hr class="colorgraph">
                    <div class="col-xs-4 col-xs-offset-5 ">
                        <p class="lead">&copy; By MON </p>
                    </div>
                </div>
            </div>
            <!-- /#page-content-wrapper -->
        </div>
        <!-- /#wrapper -->

        <!-- jQuery -->
        <script type="text/javascript" src="/Scripts/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script type="text/javascript" src="/Scripts/bootstrap.min.js"></script>
        <!-- Menu Toggle Script -->
        <script type="text/javascript">
            $("#menu-toggle").click(function(e) {
                e.preventDefault();
                $("#wrapper").toggleClass("toggled");
            });
        </script>

    </body>
</html>