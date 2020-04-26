<?php
    header('Access-Control-Allow-Origin: *');
    header('Content-Type: application/json');

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

    $genre = $_GET['genre'];

    $query = "SELECT * FROM book".' WHERE genre= :genre ';
    $stmt = $conn->prepare($query);
    $stmt->bindParam(':genre', $genre);
    $stmt->execute();

    $num = $stmt->rowCount();

    if ($num > 0) {
        $posts_arr = array();
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
          extract($row);
          $post_item = array(
            'id' => $id,
            'title' => $title,
            'author' => $author,
            'pages' => $pages,
            'genre' => $genre
          );
          // Push to "data"
          array_push($posts_arr, $post_item);
          // array_push($posts_arr['data'], $post_item);
        }
        // Turn to JSON & output
        echo json_encode($posts_arr);
      } else {
        // No Posts
        echo json_encode(
          array('message' => 'No Posts Found')
        );
      }