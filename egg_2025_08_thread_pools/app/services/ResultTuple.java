
package services;

import models.InputAccount;

class ResultTuple {
    final InputAccount inputAccount;
    final boolean doInclude;
    final String threadName;

    ResultTuple(InputAccount inputAccount, boolean doInclude, String threadName) {
        this.inputAccount = inputAccount;
        this.doInclude = doInclude;
        this.threadName = threadName;
    }
}
