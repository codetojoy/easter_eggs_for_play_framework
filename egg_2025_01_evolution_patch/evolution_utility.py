
import hashlib
import re

class Mode:
    def __init__(self):
        self.mode_ups = 1
        self.mode_downs = 2
        self.mode_unknown = 3
        self.mode = self.mode_unknown

    def set_ups(self):
        self.mode = self.mode_ups

    def set_downs(self):
        self.mode = self.mode_downs

    def is_ups(self):
        return self.mode == self.mode_ups

    def is_downs(self):
        return self.mode == self.mode_downs

class Processor:
    def __init__(self):
        self.mode = Mode()

        self.ups_str = ""
        self.downs_str = ""

    def process_line(self, line):
        ups_regex   = "^(#|--).*!Ups.*$"
        downs_regex = "^(#|--).*!Downs.*$"
        is_ups_marker = re.match(ups_regex, line) != None
        is_downs_marker = re.match(downs_regex, line) != None

        if (is_ups_marker):
            # enter Ups mode
            self.mode.set_ups()
        elif (is_downs_marker):
            # enter Downs mode
            self.mode.set_downs()
        else:
            if (self.mode.is_ups()):
                # consume as Ups
                self.ups_str = self.ups_str + line.strip() + "\n"
            elif (self.mode.is_downs()):
                # consume as Downs
                self.downs_str = self.downs_str + line.strip() + "\n"

    def process_file(self, file):
        for line in file:
            self.process_line(line)

    def get_hash(self):
        full_str = self.downs_str.strip() + self.ups_str.strip()
        return hashlib.sha1(full_str.encode()).hexdigest()

# ----------------------------
#

file = open('./conf/evolutions/default/3.sql','r')

processor = Processor()
processor.process_file(file)

print("hash: " + processor.get_hash())
