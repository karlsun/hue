// Licensed to Cloudera, Inc. under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  Cloudera, Inc. licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

$(document).ready(function () {
  $(document).on("showSubmitPopup", function (event, data) {
    $('.submit-modal').html(data);
    $('.submit-modal').modal('show');
    var _sel = $('.submit-form .control-group[rel!="popover"]:visible');
    console.log(_sel.length)
    if (_sel.length > 0) {
      $('.submit-modal .modal-body').height(_sel.length * 65 + 100);
    }
  });
});