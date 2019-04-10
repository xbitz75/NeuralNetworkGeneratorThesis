import tensorflow as tf

exec(open("python_model_2.py").read())

model.compile(optimizer='sgd',
              loss='categorical_crossentropy',
              metrics=['accuracy'])

model.summary()
