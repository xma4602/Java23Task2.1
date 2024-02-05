package com.xma.task1;

public enum PathDifferenceStatus {
    NOT_EXISTS("хотя бы один из двух путей ведет к несуществующему файлу/каталогу"),
    SAME_FILE("оба пути ведут к одному файлу"),
    BIGGER_FILE("оба пути ведут к файлу, при этом размер path1 > path2"),
    SMALLER_FILE("оба пути ведут к файлу, при этом размер path1 < path2"),
    SAME_SIZE_FILE("оба пути ведут к файлу, при этом размеры файлов совпадают"),
    SAME_DIRECTORY("оба пути ведут к одной директории"),
    SAME_ABSOLUTE_NAME_DEPTH("глубина (число частей) абсолютного пути к файлам одинаковая"),
    SAME_PREFIX("пути имеют общее начало (не считая корня)"),
    SAME_ROOT("пути имеют общий корень"),
    SUBPATH("path2 находится внутри path1"),
    PARENT_PATH("path1 находится внутри path2");

    PathDifferenceStatus(String s) {

    }
}
