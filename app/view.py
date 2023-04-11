#!/usr/bin/python3

import os
import sys
import pm4py

if __name__ == "__main__":

    net, initial_marking, final_marking = pm4py.read_pnml(os.path.join(".",".",sys.argv[1]))
    pm4py.view_petri_net(net, initial_marking, final_marking)
