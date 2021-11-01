namespace java  com.arloor.watchdog.thrift

struct Request {
    1: i32 cmd
    2: string extend
}

struct Pong {
    1: i32 cmd
    2: string msg
    3: Role role
    4: Info info
}

service Link{
    Pong ping(1: Request request)
}


enum Role {
    SENTINEL,
    NODE
}


struct Target {
    1: string host
    2: i32 port
    3: Role role
    4: bool active
}

struct Info {
    1: list<Target> targets
}