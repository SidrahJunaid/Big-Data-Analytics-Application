import pandas as pd
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
from sklearn.model_selection import cross_val_predict
rng = np.random

data='/home/sidrah/Downloads/data.csv'
col = ["HoursStudied","Score"]


training_epochs = 20
display_step = 100

data = pd.read_csv(tf.gfile.Open(data),names=col,sep=',',header=0,engine="python")

tr_X= np.asarray([i[1] for i in data.loc[:,['HoursStudied']].to_records()],dtype="float")
tr_Y= np.asarray([i[1] for i in data.loc[:,['Score']].to_records()],dtype="float")
rnd_indices = np.random.rand(len(tr_X)) < 0.80

ts_x= np.asarray([i[1] for i in data.loc[:,['HoursStudied']].to_records()],dtype="float")
ts_y= np.asarray([i[1] for i in data.loc[:,['Score']].to_records()],dtype="float")

ts_X=ts_x[rnd_indices]
ts_Y=ts_y[rnd_indices]



n_samples = tr_X.shape[0]

X = tf.placeholder("float")
Y = tf.placeholder("float")

W = tf.Variable(rng.randn(), name="weight")
b = tf.Variable(rng.randn(), name="bias")

pred = tf.add(tf.mul(X, W), b)


cost = tf.reduce_sum(tf.pow(pred-Y, 2))/(2*n_samples)

optimizer = tf.train.GradientDescentOptimizer(0.03).minimize(cost)

init = tf.initialize_all_variables()

with tf.Session() as sess:
    sess.run(init)
    # Fit all training data
    for z in range(100):
        for (x, y) in zip(tr_X, tr_Y):
            sess.run(optimizer, feed_dict={X: x, Y: y})
        #Display logs per epoch step
        if (z+1) % display_step == 0:
            c = sess.run(cost, feed_dict={X: tr_X, Y: tr_Y})
            print("Epoch:", '%04d' % (z+1), "cost=", "{:.4f}".format(c), \
                "W=", sess.run(W), "b=", sess.run(b))

    print("Optimization Finished!")
    training_cost = sess.run(cost, feed_dict={X: tr_X, Y: tr_Y})
    print("Training cost=", training_cost, "W=", sess.run(W), "b=", sess.run(b), '\n')

    print("Testing... (Mean square loss Comparison)")

    testing_cost = sess.run(
        tf.reduce_sum(tf.pow(pred - Y, 2)) / (2 * ts_X.shape[0]),
        feed_dict={X: ts_X, Y: ts_Y})  # same function as cost above
    print("Testing cost=", testing_cost)
    print("Absolute mean square loss difference:", abs(
        training_cost - testing_cost))

    #pred_y = sess.run(pred, feed_dict={X: ts_X})
    #mse = tf.reduce_mean(tf.square(pred_y - ts_Y))
    #print("MSE: %.4f" % sess.run(mse))

    #Graphic display
    plt.plot(tr_X, tr_Y, 'ro', label='Actual data')
    plt.plot(tr_X, sess.run(W) * tr_X + sess.run(b), label='Predicted line')
    plt.legend(loc='upper left')
    plt.ylabel('Score')
    plt.xlabel('Hours of Studies')
    plt.show()
