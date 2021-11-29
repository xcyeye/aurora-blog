let github_canlendar = (git_user, git_color) => {
    let git_githubapiurl = "https://python-github-calendar-api.vercel.app/api?" + git_user;
    let git_fixed = 'fixed';
    let git_px = 'px';
    let git_month = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
    let git_monthchange = [];
    let git_oneyearbeforeday = '';
    let git_thisday = '';
    let git_amonthago = '';
    let git_aweekago = '';
    let git_weekdatacore = 0;
    let git_datacore = 0;
    let git_total = 0;
    let git_datadate = '';
    let git_git_data = [];
    let git_positionplusdata = [];
    let git_firstweek = [];
    let git_lastweek = [];
    let git_beforeweek = [];
    let git_thisweekdatacore = 0;
    let git_mounthbeforeday = 0;
    let git_mounthfirstindex = 0;
    let git_crispedges = 'crispedges';
    let git_thisdayindex = 0;
    let git_amonthagoindex = 0;
    let git_amonthagoweek = [];
    let git_firstdate = [];
    let git_first2date = [];
    let git_montharrbefore = [];
    let git_monthindex = 0;

    function responsiveChart() {
        let git_tooltip_container = document.getElementById('git_tooltip_container');
        let git_x = '';
        let git_y = '';
        let git_span1 = '';
        let git_span2 = '';
        let c = document.getElementById("gitcanvas");
        let cmessage = document.getElementById("gitmessage");
        let ctx = c.getContext("2d");
        c.width = document.getElementById("gitcalendarcanvasbox").offsetWidth;
        let linemaxwitdh = 0.96 * c.width / git_data.length;
        c.height = 9 * linemaxwitdh;
        let lineminwitdh = 0.8 * linemaxwitdh;
        let setposition = {x: 0.02 * c.width, y: 0.025 * c.width};
        for (let week in git_data) {
            weekdata = git_data[week];
            for (let day in weekdata) {
                let dataitem = {date: "", count: "", x: 0, y: 0};
                git_positionplusdata.push(dataitem);
                ctx.fillStyle = git_thiscolor(git_color, weekdata[day].count);
                setposition.y = Math.round(setposition.y * 100) / 100;
                dataitem.date = weekdata[day].date;
                dataitem.count = weekdata[day].count;
                dataitem.x = setposition.x;
                dataitem.y = setposition.y;
                ctx.fillRect(setposition.x, setposition.y, lineminwitdh, lineminwitdh);
                setposition.y = setposition.y + linemaxwitdh
            }
            setposition.y = 0.025 * c.width;
            setposition.x = setposition.x + linemaxwitdh
        }
        if (document.body.clientWidth > 700) {
            ctx.font = "600  Arial";
            ctx.fillStyle = '#aaa';
            ctx.fillText("日", 0, 1.9 * linemaxwitdh);
            ctx.fillText("二", 0, 3.9 * linemaxwitdh);
            ctx.fillText("四", 0, 5.9 * linemaxwitdh);
            ctx.fillText("六", 0, 7.9 * linemaxwitdh);
            let monthindexlist = c.width / 24;
            for (let index in git_monthchange) {
                ctx.fillText(git_monthchange[index], monthindexlist, 0.7 * linemaxwitdh);
                monthindexlist = monthindexlist + c.width / 12
            }
        }
        c.onmousemove = function (event) {
            if (document.querySelector('.gitmessage')) {
                git_tooltip_container.innerHTML = ""
            }
            getMousePos(c, event)
        };
        git_tooltip_container.onmousemove = function (event) {
            if (document.querySelector('.gitmessage')) {
                git_tooltip_container.innerHTML = ""
            }
        };

        function getMousePos(canvas, event) {
            var rect = canvas.getBoundingClientRect();
            var x = event.clientX - rect.left * (canvas.width / rect.width);
            var y = event.clientY - rect.top * (canvas.height / rect.height);
            for (let item of git_positionplusdata) {
                let lenthx = x - item.x;
                let lenthy = y - item.y;
                if (0 < lenthx && lenthx < lineminwitdh) {
                    if (0 < lenthy && lenthy < lineminwitdh) {
                        git_span1 = item.date;
                        git_span2 = item.count;
                        git_x = event.clientX - 100;
                        git_y = event.clientY - 60;
                        html = tooltip_html(git_x, git_y, git_span1, git_span2);
                        append_div_gitcalendar(git_tooltip_container, html)
                    }
                }
            }
        }
    }

    function addlastmonth() {
        if (git_thisdayindex === 0) {
            thisweekcore(52);
            thisweekcore(51);
            thisweekcore(50);
            thisweekcore(49);
            thisweekcore(48);
            git_thisweekdatacore += git_firstdate[6].count;
            git_amonthago = git_firstdate[6].date
        } else {
            thisweekcore(52);
            thisweekcore(51);
            thisweekcore(50);
            thisweekcore(49);
            thisweek2core();
            git_amonthago = git_first2date[git_thisdayindex - 1].date
        }
    }

    function thisweek2core() {
        for (let i = git_thisdayindex - 1; i < git_first2date.length; i++) {
            git_thisweekdatacore += git_first2date[i].count
        }
    }

    function thisweekcore(index) {
        for (let item of git_data[index]) {
            git_thisweekdatacore += item.count
        }
    }

    function addlastweek() {
        for (let item of git_lastweek) {
            git_weekdatacore += item.count
        }
    }

    function addbeforeweek() {
        for (let i = git_thisdayindex; i < git_beforeweek.length; i++) {
            git_weekdatacore += git_beforeweek[i].count
        }
    }

    function addweek(data) {
        if (git_thisdayindex === 6) {
            git_aweekago = git_lastweek[0].date;
            addlastweek()
        } else {
            lastweek = data.contributions[51];
            git_aweekago = lastweek[git_thisdayindex + 1].date;
            addlastweek();
            addbeforeweek()
        }
    }

    fetch(git_githubapiurl).then(data => data.json()).then(data => {
        git_data = data.contributions;
        git_total = data.total;
        git_first2date = git_data[48];
        git_firstdate = git_data[47];
        git_firstweek = data.contributions[0];
        git_lastweek = data.contributions[52];
        git_beforeweek = data.contributions[51];
        git_thisdayindex = git_lastweek.length - 1;
        git_thisday = git_lastweek[git_thisdayindex].date;
        git_oneyearbeforeday = git_firstweek[0].date;
        git_monthindex = git_thisday.substring(5, 7) * 1;
        git_montharrbefore = git_month.splice(git_monthindex, 12 - git_monthindex);
        git_monthchange = git_montharrbefore.concat(git_month);
        addweek(data);
        addlastmonth();
        let html = github_main_box(git_monthchange, git_data, git_user, git_color, git_total, git_thisweekdatacore, git_weekdatacore, git_oneyearbeforeday, git_thisday, git_aweekago, git_amonthago);
        append_div_gitcalendar(github_container, html);
        document.getElementById('github_loading').innerHTML = "";
        responsiveChart()
    }).catch(function (error) {
        console.log(error)
    });
    window.onresize = function () {
        responsiveChart()
    };
    window.onscroll = function () {
        if (document.querySelector('.gitmessage')) {
            git_tooltip_container.innerHTML = ""
        }
    };
    let git_thiscolor = (color, x) => {
        if (x === 0) {
            let i = parseInt(x / 2);
            return color[0]
        } else if (x < 2) {
            return color[1]
        } else if (x < 20) {
            let i = parseInt(x / 2);
            return color[i]
        } else {
            return color[9]
        }
    };
    let tooltip_html = (x, y, span1, span2) => {
        let html = '';
        html += '<div class="gitmessage" style="top:' + y + 'px;left:' + x + 'px;position: fixed;z-index:9999"><div class="angle-wrapper" style="display:block;"><span>' + span1 + '&nbsp;</span><span>' + span2 + ' 次上传</span></div></div>';
        return html
    };
    let github_canvas_box = () => {
        let html = '<div id="gitcalendarcanvasbox"> <canvas id="gitcanvas" style="animation: none;"></canvas></div>';
        return html
    };
    let github_info_box = (user, color) => {
        let html = '';
        html += '<div id="git_tooltip_container"></div><div class="contrib-footer clearfix mt-1 mx-3 px-3 pb-1"><div class="float-left text-gray">数据来源 <a href="https://github.com/' + user + '" target="blank">@' + user + '</a></div><div class="contrib-legend text-gray">Less <ul class="legend"><li style="background-color:' + color[0] + '"></li><li style="background-color:' + color[2] + '"></li><li style="background-color:' + color[4] + '"></li><li style="background-color:' + color[6] + '"></li><li style="background-color:' + color[8] + '"></li></ul>More </div></div>';
        return html
    };
    let github_main_box = (monthchange, git_data, user, color, total, thisweekdatacore, weekdatacore, oneyearbeforeday, thisday, aweekago, amonthago) => {
        let html = '';
        let canvasbox = github_canvas_box();
        let infobox = github_info_box(user, color);
        let style = github_main_style();
        html += '<div class="position-relative"><div class="border py-2 graph-before-activity-overview"><div class="js-gitcalendar-graph mx-md-2 mx-3 d-flex flex-column flex-items-end flex-xl-items-center overflow-hidden pt-1 is-graph-loading graph-canvas gitcalendar-graph height-full text-center">' + canvasbox + '</div>' + infobox + '</div></div>';
        html += '<div style="display:flex;width:100%"><div class="contrib-column contrib-column-first table-column"><span class="text-muted">过去一年提交</span><span class="contrib-number">' + total + '</span><span class="text-muted">' + oneyearbeforeday + '&nbsp;-&nbsp;' + thisday + '</span></div><div class="contrib-column table-column"><span class="text-muted">最近一月提交</span><span class="contrib-number">' + thisweekdatacore + '</span><span class="text-muted">' + amonthago + '&nbsp;-&nbsp;' + thisday + '</span></div><div class="contrib-column table-column"><span class="text-muted">最近一周提交</span><span class="contrib-number">' + weekdatacore + '</span><span class="text-muted">' + aweekago + '&nbsp;-&nbsp;' + thisday + '</span></div></div>' + style;
        return html
    };
    let github_main_style = () => {
        style = '<style>#github_container{text-align:center;margin:0 auto;width:100%;display:flex;display:-webkit-flex;justify-content:center;align-items:center;flex-wrap:wrap;}.gitcalendar-graph text.wday,.gitcalendar-graph text.month{font-size:10px;fill:#aaa;}.contrib-legend{text-align:right;padding:0 14px 10px 0;display:inline-block;float:right;}.contrib-legend .legend{display:inline-block;list-style:none;margin:0 5px;position:relative;bottom:-1px;padding:0;}.contrib-legend .legend li{display:inline-block;width:10px;height:10px;}.text-small{font-size:12px;color:#767676;}.gitcalendar-graph{padding:15px 0 0;text-align:center;}.contrib-column{text-align:center;border-left:1px solid #ddd;border-top:1px solid #ddd;font-size:11px;}.contrib-column-first{border-left:0;}.table-column{padding:10px;display:table-cell;flex:1;vertical-align:top;}.contrib-number{font-weight:300;line-height:1.3em;font-size:24px;display:block;}.gitcalendar img.spinner{width:70px;margin-top:50px;min-height:70px;}.monospace{text-align:center;color:#000;font-family:monospace;}.monospace a{color:#1D75AB;text-decoration:none;}.contrib-footer{font-size:11px;padding:0 10px 12px;text-align:left;width:100%;box-sizing:border-box;height:26px;}.left.text-muted{float:left;margin-left:9px;color:#767676;}.left.text-muted a{color:#4078c0;text-decoration:none;}.left.text-muted a:hover,.monospace a:hover{text-decoration:underline;}h2.f4.text-normal.mb-3{display:none;}.float-left.text-gray{float:left;}#user-activity-overview{display:none;}.day-tooltip{white-space:nowrap;position:absolute;z-index:99999;padding:10px;font-size:12px;color:#959da5;text-align:center;background:rgba(0,0,0,.85);border-radius:3px;display:none;pointer-events:none;}.day-tooltip strong{color:#dfe2e5;}.day-tooltip.is-visible{display:block;}.day-tooltip:after{position:absolute;bottom:-10px;left:50%;width:5px;height:5px;box-sizing:border-box;margin:0 0 0 -5px;content:" ";border:5px solid transparent;border-top-color:rgba(0,0,0,.85)}.position-relative{width:100%;}@media screen and (max-width:650px){.contrib-column{display:none}}.angle-wrapper{z-index:9999;display:inline;width:200px;height:40px;position:relative;padding:5px 0;background:rgba(0,0,0,0.8);border-radius:8px;text-align:center;color:white;}.angle-box{position:fixed;padding:10px}.angle-wrapper span{padding-bottom:1em;}.angle-wrapper:before{content:"";width:0;height:0;border:10px solid transparent;border-top-color:rgba(0,0,0,0.8);position:absolute;left:47.5%;top:100%;}</style>';
        return style
    }
};
let append_div_gitcalendar = (parent, text) => {
    let temp = document.createElement('div');
    temp.innerHTML = text;
    let frag = document.createDocumentFragment();
    while (temp.firstChild) {
        //console.log("-------------")
        //console.log(temp.firstChild)
        frag.appendChild(temp.firstChild)
    }
    parent.appendChild(frag)
};
let loading_git = (color) => {
    return '<div id="github_loading" style="width:10%;height:100%;margin:0 auto;display: block"><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"  viewBox="0 0 50 50" style="enable-background:new 0 0 50 50" xml:space="preserve"><path fill="' + color + '" d="M25.251,6.461c-10.318,0-18.683,8.365-18.683,18.683h4.068c0-8.071,6.543-14.615,14.615-14.615V6.461z" transform="rotate(275.098 25 25)"><animateTransform attributeType="xml" attributeName="transform" type="rotate" from="0 25 25" to="360 25 25" dur="0.6s" repeatCount="indefinite"></animateTransform></path></svg></div>';
};

module.exports = {
    github: function (username) {
        let git_user = 'qsyyke';
        let github_container = document.getElementById('github_container');
        let github_loading = document.getElementById('github_loading');
        let git_purple = ['#ebedf0', '#fdcdec', '#fc9bd9', '#fa6ac5', '#f838b2', '#f5089f', '#c4067e', '#92055e', '#540336', '#48022f', '#30021f',];
        let git_green = ['#ebedf0', '#f0fff4', '#dcffe4', '#bef5cb', '#85e89d', '#34d058', '#28a745', '#22863a', '#176f2c', '#165c26', '#144620'];
        let git_blue = ['#ebedf0', '#f1f8ff', '#dbedff', '#c8e1ff', '#79b8ff', '#2188ff', '#0366d6', '#005cc5', '#044289', '#032f62', '#05264c',];
        let git_color = git_green;
        append_div_gitcalendar(github_container, loading_git(git_color[4]));
        //github_canlendar(git_user, git_color)
    }
}
