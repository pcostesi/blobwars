#!/usr/bin/env python
# -*- coding: utf-8 -*-

from itertools import izip as zip, ifilter as filter, imap as map, product
import logging

logging.basicConfig(level='INFO')

def distance(src, dst):
    """ Given two tuples, returns the maximum manhattan distance. """
    return max(map(lambda x: abs(x[0] - x[1]), zip(src, dst)))

    
class Matrix(object):
    def __init__(self, size=8, matrix=None):
        """ Creates a matrix full of zeroes (or a copy of a list of lists). """
        if not matrix:
            self.size = size;
            self.matrix = [[0] * size for i in xrange(size)]
            logging.debug("Creating a new empty matrix of size %i.", size)
        else:
            self.matrix = [list(row) for row in matrix]
            self.size = min(len(matrix), *map(len, self.matrix))
            logging.debug("Creating a new matrix using list of lists")
        
    def __enumerate(self):
        for y, row in enumerate(self.matrix):
            for x, item in enumerate(row):
                yield (x, y, item)
                
    def __iter__(self):
        """ Return the contents of a matrix as an iterator of (x, y, item). """
        return iter(self.__enumerate())
        
    def __setitem__(self, index, value):
        """ Sets an item (x, y) in the matrix"""
        x, y = index
        logging.debug("Setting (%i, %i) to %s.", x, y, value)
        self.matrix[y][x] = value
        
    def __delitem__(self, index):
        """ Zeroes out a slot. """
        logging.debug("Deleting (%i, %i) in matrix.", *index)
        self[index] = 0
    
    def __getitem__(self, index):
        """ Returns the item at self[x, y]. """
        x, y = index
        return self.matrix[y][x]
    
    def points_in_radius(self, point, radius=1, include_self=False):
        """ return a set of points inside radius `radius' of `point'."""
        logging.debug("Returning points in radius %i for (%i, %i)", radius, *point)
        matrix = self.matrix
        min_x = min(map(len, self.matrix))
        # keep only the points inside the matrix.
        keep = lambda x: 0 <= x[0] < min_x and 0 <= x[1] < len(matrix)
        # the cartesian product of [-radius, radius]
        diff = range(-radius, radius + 1)
        points = ((point[0] + x, point[1] + y) for x, y in product(diff, diff))
        points = filter(keep, points)
        if not include_self:
            points = filter(lambda x: x != point, points)
        return points
    

class Board(Matrix):
    COMPUTER = 1
    HUMAN = -1
    EMPTY = 0

    def __init__(self, size=8, matrix=None, empty=False):
        super(Board, self).__init__(size, matrix)
        if not matrix and not empty:
            logging.debug("Creating pre-filled default board.")
            self[0, 0] = Board.HUMAN
            self[0, 7] = Board.HUMAN
            self[7, 0] = Board.COMPUTER
            self[7, 7] = Board.COMPUTER

    def __piece(self, is_computer):
        return Board.COMPUTER if is_computer else Board.HUMAN

    def __valid_move(self, src, dst):
        if src != dst and distance(src, dst) <= 2:
            return self[src] != Board.EMPTY and self[dst] == Board.EMPTY
        return False

    def __attack(self, point):
        """ Switch all neighbours to `point's team. """
        logging.debug("Attacking neighbours for (%i, %i):", *point)
        for x, y in self.points_in_radius(point, radius=1):
            if self[x, y] != Board.EMPTY:
                logging.debug("* Attacking (%i, %i)", x, y)
                self[x, y] = self[point]

    def move(self, src, dst):
        """ Destructively move a blob from src (x1, y1) to dst (x2, y2). """
        logging.debug("Moving (%i, %i) to (%i, %i)", src[0], src[1], dst[0], dst[1])
        if not self.__valid_move(src, dst):
            return None
        self[dst] = self[src]
        if distance(src, dst) == 2:
            del self[src]
        self.__attack(dst)
            
    def __moves_for_point(self, src):
        """ Return all the possible vaid moves for a given blob. """
        logging.debug("Calculating possible moves for blob (%i, %i)", *src)
        points = self.points_in_radius(src, radius=2)
        return filter(lambda x: self.__valid_move(src, x), points)

    def children(self, is_computer=True):
        """ Return an iterator with all the possible boards for a player. """
        logging.debug("Generating children matrices:")
        piece = self.__piece(is_computer)
        points = filter(lambda x: x[2] == piece, self)
        for src in map(lambda x: x[:2], points):
            for dst in self.__moves_for_point(src):
                b = Board(matrix=self.matrix)
                b.move(src, dst)
                logging.debug("Board:\n %s\nMove: %s", b, (src, dst))
                yield b, (src, dst)
                
    def __str__(self):
        """ Pretty print a board. """
        # this convers the numbers to characters
        chars = {-1: "H", 0: " ", 1: "C"}
        matrix = self.matrix;
        if not matrix:
            return ""
        # create a ruler to separate each row
        rule = "+---" * len(matrix[0]) + "+"
        lines = [rule]
        for row in matrix:
            lines.append("| " + ' | '.join(map(chars.get, row) ) + " |")
            lines.append(rule)
        return '\n'.join(lines)
                
    @property
    def score(self):
        """ Returns the difference of computer and player blobs. """ 
        score = sum(map(lambda x: x[2], self))
        logging.debug("Score is %i." % score)
        return score
        
    @property
    def max_score(self):
        return self.size ** 2;
            

def minimax(board, level, maximize=True, is_computer=True):
    """ Naïve minimax solver. """
    if level == 0:
        logging.debug("Reached level 0, returning board score.")
        return board.score, None
    # set the worst score possible for this player.
    score = -board.max_score if maximize else board.max_score
    logging.debug("Maximizing: %s", maximize)
    best_move = None
    for child, move in board.children(is_computer=is_computer):
        local, _ = minimax(child, level - 1, not maximize, not is_computer)
        # update the score when a better local solution is found
        if (maximize and score < local) or (not maximize and score > local):
            score = local
            best_move = move
            logging.debug("New score: %i, move: %s", score, move)
    if best_move is None:
        return board.score, None
    logging.debug("Score: %d, \tSelected move: %s", score, best_move)
    return score, best_move
    
    
def alphabeta(board, level, alpha, beta, maximize=True, is_computer=True):
    """ Minimax with Alpha-Beta Pruning. """
    if level == 0:
        logging.debug("Reached level 0, returning board score.")
        return board.score, None
    logging.debug("Maximizing" if maximize else "Minimizing")
    # we base the score on the previous runs (alpha-beta window)
    score = alpha if maximize else beta
    logging.debug("Initial score set to %i", score)
    best_move = None
    for child, move in board.children(is_computer):
        # find a new local solution
        local, _ = alphabeta(child, level - 1, alpha, beta, 
                not maximize, not is_computer)
        # Update the score when a better local solution is found
        if (maximize and score < local) or (not maximize and score > local):
            score = local
            best_move = move
            logging.debug("New score: %i, move: %s", score, move)
        # Update alpha or beta (this narrows the alpha-beta window)
        if maximize:
            alpha = max(score, alpha)
            logging.debug("Updating alpha to %i.", alpha)
        else:
            beta = min(score, beta)
            logging.debug("Updating beta to %i.", beta)
        # If we fall outside the alpha-beta window, return.
        if (maximize and score >= beta) or (not maximize and score <= alpha):
            logging.debug("Cutoff (%s, alpha: %i, beta: %i)", 
                    "Maximizing" if maximize else "Minimizing", alpha, beta)
            break
    logging.debug("Score: %d, \tSelected move: %s", score, best_move)
    return score, best_move
    
    
def time_minimax():
    for i in range(1, 4):
        b = Board()
        mm = minimax(b, i)
        abp = alphabeta(b, i, -b.max_score, b.max_score)
        print "alpha beta\t", abp
        print "minimax\t\t", mm
        assert(abp == mm)
    
if __name__ == "__main__":
    import cProfile
    cProfile.run("time_minimax()")
    
    #cProfile.run("minimax(Board(), 4)")
    #cProfile.run("alphabeta(Board(), 4, -65, 65)")
