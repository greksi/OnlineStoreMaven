#!/bin/bash
echo "Hello word!!!"





echo 'Copy files...'
ssh grek@45.84.227.249 << EOF

cd ~;
echo ls -la;
read;

EOF



