<?php
    header('Access-Control-Allow-Origin: *');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: POST');


    //asa se defineste o variabila
    $host = "localhost";
    $db_name = "PersonalLibrary";
    $username = "root";
    $password = "";
    $conn = null;

    try { 
        $conn = new PDO('mysql:host=' . $host . ';dbname=' . $db_name, $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    } catch(PDOException $e) {
        echo 'Connection Error: ' . $e->getMessage();
    }
    
    $data = json_decode(file_get_contents('php://input'));

    $query1= "INSERT INTO book ".' SET title = :title, author = :author, pages = :pages, genre = :genre';
    $stmt = $conn->prepare($query1);
    $stmt->bindParam(':title', $data->title);
    $stmt->bindParam(':author', $data->author);
    $stmt->bindParam(':pages', $data->pages);
    $stmt->bindParam(':genre', $data->genre);
    if($stmt->execute()){
        echo json_encode(
            array('message' => 'Book Created')
        );
    }else{
        echo json_encode(
            array('message' => 'Book Created')
        );
    }