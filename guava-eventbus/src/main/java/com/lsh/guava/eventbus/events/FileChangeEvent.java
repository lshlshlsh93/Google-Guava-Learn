package com.lsh.guava.eventbus.events;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @Author lishaohui
 * @Date 2023/5/31 10:29
 */
public class FileChangeEvent {

    private final Path path;

    private final WatchEvent.Kind<?> kind;

    public FileChangeEvent(Path path, WatchEvent.Kind<?> kind) {
        this.path = path;
        this.kind = kind;
    }

    public Path getPath() {
        return path;
    }

    public WatchEvent.Kind<?> getKind() {
        return kind;
    }

}
