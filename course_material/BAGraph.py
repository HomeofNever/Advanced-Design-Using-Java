import networkx as nx
import csv

if __name__ == "__main__":
    print('This program will generate a Barabási–Albert preferential attachment graph '
          'and save it in the adjacency list format.')
    n, m = [int(val) for val in input("Enter the values for n and m: ").split()]
    output_file_name = input("Enter the name for the output file: ")
    # G = nx.complete_graph(10)
    G = nx.barabasi_albert_graph(n, m)
    print(str(len(G.nodes())) + ' nodes and ' + str(len(G.edges())) + " edges")
    G1 = nx.relabel_nodes(G, {label: '"' + str(label) + '"' for label in G.nodes()})
    nx.write_edgelist(G1, output_file_name, delimiter=',', data=False)
