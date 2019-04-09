import tensorflow as tf

exec(open("python_model_3.py").read())

model.compile(optimizer='sgd',
              loss='categorical_crossentropy',
              metrics=['accuracy'])

model.summary()
