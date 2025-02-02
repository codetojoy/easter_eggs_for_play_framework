
import hashlib
import re

class Processor:
    def __init__(self):
        self.mode_ups = 1
        self.mode_downs = 2
        self.mode_unknown = -1
        self.mode = self.mode_unknown

        self.ups_str = ""
        self.downs_str = ""

    def process_line(self, line):
        ups_regex   = "^(#|--).*!Ups.*$"
        downs_regex = "^(#|--).*!Downs.*$"
        does_ups_match = re.match(ups_regex, line) != None
        does_downs_match = re.match(downs_regex, line) != None

        if (does_ups_match):
            # enter Ups mode
            self.mode = self.mode_ups
        elif (does_downs_match):
            # enter Downs mode
            self.mode = self.mode_downs
        else:
            if (self.mode == self.mode_ups):
                # consume as Ups
                self.ups_str = self.ups_str + line.strip() + "\n"
            elif (self.mode == self.mode_downs):
                # consume as Downs
                self.downs_str = self.downs_str + line.strip() + "\n"

    def process_file(self, file):
        for line in file:
            self.process_line(line)

    def get_hash(self):
        full_str = self.downs_str.strip() + self.ups_str.strip()
        return hashlib.sha1(full_str.encode()).hexdigest()

    def get_ups(self):
        return self.ups_str

    def get_downs(self):
        return self.downs_str

file = open('./conf/evolutions/default/3.sql','r')
processor = Processor()
processor.process_file(file)

print("hash: " + processor.get_hash())