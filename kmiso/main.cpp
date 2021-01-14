#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <set>

using namespace std;

const int INF = 2 * 1e9;

struct Node {
    int from;
    int to;
    int c;
    int f;

    Node();

    Node(const Node&);

    Node(int, int, int, int);

    Node& operator=(const Node&);
};

struct Node2 {
    int vertex;
    int from;
    int sign;
    int data;

    Node2();

    Node2(const Node2&);

    Node2(int, int, int, int);

    Node2& operator=(const Node2&);
};

void input_graph(vector<Node>&, int&, int&);

char int_to_letter(int, int);

int main() {
    ofstream fout("output.txt");
    int n, m;
    vector<Node> graph;
    input_graph(graph, n, m);
    int v_now{0};
    vector<int> used(n);
    vector<Node2> q;
    for (int i{0}; i < m; ++i) {
        if (graph[i].to == (n - 1))
            v_now += graph[i].f;
    }
    while (true) {
        fout << "v = " << v_now << endl;
        fill(used.begin(), used.end(), 0);
        q.clear();
        q.push_back(Node2{0, 0, 1, INF});
        int index = 0;
        used[0] = 1;
        while (true) {
            int vertex = q[index].vertex;
            for (int i{0}; i < m; ++i) {
                if ((graph[i].from == vertex) && (used[graph[i].to] == 0) && (graph[i].f < graph[i].c)) {
                    q.push_back(Node2{graph[i].to, vertex, 1, graph[i].c - graph[i].f});
                    used[graph[i].to] = 1;
                }
                if ((graph[i].to == vertex) && (used[graph[i].from] == 0) && (graph[i].f > 0)) {
                    q.push_back(Node2{graph[i].from, vertex, 0, graph[i].f});
                    used[graph[i].from] = 1;
                }
            }
            used[vertex] = 2;
            bool flag = false;
            for (int i{0}; i < n; ++i)
                if (used[i] == 1) {
                    flag = true;
                    break;
                }
            if ((used[n - 1] == 0) && flag) {
                ++index;
            } else
                break;
        }
        for (int i{0}; i < q.size(); ++i) {
            fout << int_to_letter(q[i].vertex, n) << " " << int_to_letter(q[i].from, n) << " "
                 << ((q[i].sign == 1) ? '+' : '-') << " "
                 << ((q[i].data == INF) ? -1 : q[i].data)
                 << ((used[q[i].vertex] == 2) ? " ✓" : "") << endl;
        }
        if (used[n - 1] == 1) {
            vector<pair<int, int>> tmp;
            int vertex = n - 1;
            int e1{INF}, e2{INF}, e;
            while (vertex != 0) {
                for (int i{0}; i < q.size(); ++i) {
                    if (q[i].vertex == vertex) {
                        tmp.push_back({q[i].from, vertex});
                        fout << int_to_letter(vertex, n) << " " << ((q[i].sign == 1) ? "<-" : "->") << " " << q[i].data
                             << " " << ((q[i].sign == 1) ? "<-" : "->") << " ";
                        if (q[i].sign == 1)
                            e1 = min(e1, q[i].data);
                        else
                            e2 = min(e2, q[i].data);
                        vertex = q[i].from;
                        break;
                    }
                }
            }
            fout << int_to_letter(vertex, n) << endl;
            e = min(e1, e2);
            fout << "e1 = " << ((e1 == INF) ? -1 : e1) << endl;
            fout << "e2 = " << ((e2 == INF) ? -1 : e2) << endl;
            fout << "e = " << e << endl;
            v_now += e;
            for (int k{0}; k < tmp.size(); ++k) {
                for (int i{0}; i < m; ++i) {
                    if ((graph[i].from == tmp[k].first) && (graph[i].to == tmp[k].second)) {
                        graph[i].f += e;
                    }
                    if ((graph[i].from == tmp[k].second) && (graph[i].to == tmp[k].first)) {
                        graph[i].f -= e;
                    }
                }
            }
            fout << endl;
            continue;
        }
        break;
    }
    fout << "v_max = " << v_now << endl;
    fout << "X = {";
    set<int> vertices;
    for (int i{0}; i < n; ++i) {
        if (used[i] == 2) {
            vertices.insert(i);
            if (i == 0)
                fout << "s, ";
            else if (i == (n - 1))
                fout << "t, ";
            else
                fout << (char) ('a' + i - 1) << ", ";
        }
    }
    fout << "}" << endl;
    fout << "~X = V / X" << endl;
    fout << "(X, ~X) = { ";
    for (int i{0}; i < m; ++i) {
        if ((vertices.find(graph[i].from) != vertices.end()) && (vertices.find(graph[i].to) == vertices.end())) {
            fout << "(";
            if (graph[i].from == 0)
                fout << "s, ";
            else if (graph[i].from == (n - 1))
                fout << "t, ";
            else
                fout << (char) ('a' + graph[i].from - 1) << ", ";
            if (graph[i].to == 0)
                fout << "s), ";
            else if (graph[i].to == (n - 1))
                fout << "t), ";
            else
                fout << (char) ('a' + graph[i].to - 1) << "), ";
        }
    }
    fout << "}" << endl << endl;
    fout.close();
    return 0;
}

void input_graph(vector<Node>& graph, int& n, int& m) {
    ifstream fin("input.txt");
    fin >> n >> m;
    graph.resize(m);
    for (int i{0}; i < m; ++i) {
        Node node;
        char tmp;
        fin >> tmp;
        switch (tmp) {
            case 's':
                node.from = 0;
                break;
            case 't':
                node.from = n - 1;
                break;
            default:
                node.from = tmp - 'a' + 1;
                break;
        }
        fin >> tmp;
        switch (tmp) {
            case 's':
                node.to = 0;
                break;
            case 't':
                node.to = n - 1;
                break;
            default:
                node.to = tmp - 'a' + 1;
                break;
        }
        fin >> node.c >> node.f;
        graph[i] = node;
    }
    fin.close();
}

char int_to_letter(int vertex, int n) {
    if (vertex == 0)
        return 's';
    else if (vertex == (n - 1))
        return 't';
    else
        return (char) ('a' + vertex - 1);
}

Node::Node() : from{0}, to{0}, c{0}, f{0} {}

Node::Node(const Node& node) : from{node.from}, to{node.to}, c{node.c}, f{node.f} {}

Node::Node(int _from, int _to, int _c, int _f) : from{_from}, to{_to}, c{_c}, f{_f} {}

Node& Node::operator=(const Node& node) {
    if (this == &node)
        return *this;
    this->from = node.from;
    this->to = node.to;
    this->c = node.c;
    this->f = node.f;
    return *this;
}

Node2::Node2() : vertex{0}, from{0}, sign{0}, data{0} {}

Node2::Node2(const Node2& node) : vertex{node.vertex}, from{node.from}, sign{node.sign}, data{node.data} {}

Node2::Node2(int _vertex, int _from, int _sign, int _data) : vertex{_vertex}, from{_from}, sign{_sign}, data{_data} {}

Node2& Node2::operator=(const Node2& node) {
    if (this == &node)
        return *this;
    this->vertex = node.vertex;
    this->from = node.from;
    this->sign = node.sign;
    this->data = node.data;
    return *this;
}
