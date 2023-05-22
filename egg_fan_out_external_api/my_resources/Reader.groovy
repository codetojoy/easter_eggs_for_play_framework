
// acct-5151	1	false	137	3.069 seconds elapsed

def threadIds = new HashSet<Integer>()

assert args.size() > 0 
def file = args[0]

new File(file).eachLine { line ->
    if (line.trim().isEmpty()) { return } 

    def trueRegex = /.*true\s+(\d+)\s.*/
    def falseRegex = /.*false\s+(\d+)\s.*/
    def trueMatcher = (line =~ trueRegex)
    def falseMatcher = (line =~ falseRegex)

    assert (trueMatcher.matches() || falseMatcher.matches())
    if (trueMatcher.matches()) {
        def threadId = trueMatcher[0][1]
        threadIds.add(threadId)
    }
    if (falseMatcher.matches()) {
        def threadId = falseMatcher[0][1]
        threadIds.add(threadId)
    }
}

println "TRACER # thread ids: " + threadIds.size()
