namespace java  com.arloor.watchdog.thrift

struct Request {
    1: i32 cmd
    2: string extend
}

struct Response {
    1: i32 status
    2: string msg
    3: list<Report> reports
}

service Link{
    Response ping(1: Request request)
}

struct Report {
    1: string host
    2: i32 port
    3: bool active
}